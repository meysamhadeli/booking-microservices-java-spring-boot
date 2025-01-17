package buildingblocks.testbase;

import buildingblocks.mediator.abstractions.IMediator;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @Autowired
    private IMediator mediator;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    protected TestFixture fixture;

    @PostConstruct
    public void setupFixture() {
        this.fixture = new TestFixture(mediator);
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", TestContainers.postgres::getJdbcUrl);
        registry.add("spring.datasource.username", TestContainers.postgres::getUsername);
        registry.add("spring.datasource.password", TestContainers.postgres::getPassword);
        registry.add("spring.rabbitmq.host", TestContainers.rabbitMq::getHost);
        registry.add("spring.rabbitmq.port", () -> TestContainers.rabbitMq.getAmqpPort().toString());
        registry.add("spring.data.mongodb.uri", TestContainers.mongoDb::getReplicaSetUrl);
    }

    @BeforeAll
    static void init() {
        TestContainers.postgres.start();
        TestContainers.rabbitMq.start();
        TestContainers.mongoDb.start();
    }

    @AfterAll
    static void dispose() {
        TestContainers.postgres.stop();
        TestContainers.rabbitMq.stop();
        TestContainers.mongoDb.stop();
    }


    @AfterEach
    public void cleanDatabase() {
        // Query all user tables in the 'public' schema
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public'",
                String.class
        );

        // Truncate all tables dynamically
        tables.forEach(table ->
                jdbcTemplate.execute("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE")
        );

        mongoTemplate.getDb().drop();
    }
}