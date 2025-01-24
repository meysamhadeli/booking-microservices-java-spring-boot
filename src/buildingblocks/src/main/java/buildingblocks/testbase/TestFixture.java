package buildingblocks.testbase;

import buildingblocks.mediator.abstractions.IMediator;
import buildingblocks.mediator.abstractions.commands.ICommand;
import buildingblocks.mediator.abstractions.queries.IQuery;
import buildingblocks.mediator.abstractions.requests.IRequest;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TestFixture {
    private static final int DEFAULT_TIMEOUT_SECONDS = 120;
    private static final int POLLING_INTERVAL_MILLISECONDS = 100;

    private final IMediator mediator;
    private final PersistMessageProcessor persistMessageProcessor;
    private final JdbcTemplate jdbcTemplate;
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    private final RabbitAdmin rabbitAdmin;

    public TestFixture(ApplicationContext applicationContext) {
        this.mediator = applicationContext.getBean(IMediator.class);
        this.persistMessageProcessor = applicationContext.getBean(PersistMessageProcessor.class);
        this.jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        this.mongoTemplate = applicationContext.getBean(MongoTemplate.class);
        this.restTemplate = applicationContext.getBean(RestTemplate.class);
        this.rabbitAdmin = new RabbitAdmin(applicationContext.getBean(ConnectionFactory.class));
    }


    public <TResponse> TResponse send(IRequest<TResponse> request) {
        return switch (request) {
            case ICommand<TResponse> command -> mediator.send(command);
            case IQuery<TResponse> query -> mediator.send(query);
            default -> mediator.send(request);
        };
    }

    public boolean messageIsPublished(Class<?> messageType) {
        return waitForCondition(() -> persistMessageProcessor.messageIsPublished(messageType));
    }

    public boolean messageIsDelivered(Class<?> messageType) {
        return waitForCondition(() -> persistMessageProcessor.messageIsDelivered(messageType));
    }

    protected void cleanupJpa() {
        // Query all user tables in the 'public' schema
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public'",
                String.class
        );

        // Truncate all tables dynamically
        tables.forEach(table ->
                jdbcTemplate.execute("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE")
        );
    }

    protected void cleanupMongo() {
        mongoTemplate.getDb().drop();
    }

    protected void cleanupRabbitmq() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(TestContainers.rabbitMq.getAdminUsername(), TestContainers.rabbitMq.getAdminPassword());

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        int managementPort = TestContainers.rabbitMq.getMappedPort(15672);
        String host = TestContainers.rabbitMq.getHost();

        try {
            // Get and delete bindings
            String bindingsUrl = String.format("http://%s:%d/api/bindings", host, managementPort);
            ResponseEntity<List> bindingsResponse = restTemplate.exchange(bindingsUrl, HttpMethod.GET, httpEntity, List.class);
            List<Map<String, Object>> bindings = bindingsResponse.getBody();
            if (bindings != null) {
                for (Map<String, Object> binding : bindings) {
                    Binding bindingToRemove = new Binding(
                            (String) binding.get("destination"),
                            Binding.DestinationType.valueOf(((String) binding.get("destination_type")).toUpperCase()),
                            (String) binding.get("source"),
                            (String) binding.get("routing_key"),
                            (Map<String, Object>) binding.get("arguments")
                    );
                    rabbitAdmin.removeBinding(bindingToRemove);
                }
            }

            // Get and delete queues
            String queueUrl = String.format("http://%s:%d/api/queues", host, managementPort);
            ResponseEntity<List> queuesResponse = restTemplate.exchange(queueUrl, HttpMethod.GET, httpEntity, List.class);
            List<Map<String, Object>> queues = queuesResponse.getBody();
            if (queues != null) {
                for (Map<String, Object> queue : queues) {
                    String queueName = (String) queue.get("name");
                    rabbitAdmin.deleteQueue(queueName);
                }
            }
        } catch (Exception e) {
            System.err.println("Error cleaning up RabbitMQ: " + e.getMessage());
        }
    }

    private boolean waitForCondition(Supplier<Boolean> condition) {
        Instant startTime = Instant.now();
        boolean conditionMet = condition.get();

        while (!conditionMet) {
            if (Duration.between(startTime, Instant.now()).getSeconds() > DEFAULT_TIMEOUT_SECONDS) {
                return false; // Timeout expired
            }

            try {
                Thread.sleep(POLLING_INTERVAL_MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted while waiting for condition", e);
            }

            conditionMet = condition.get();
        }

        return true;
    }
}