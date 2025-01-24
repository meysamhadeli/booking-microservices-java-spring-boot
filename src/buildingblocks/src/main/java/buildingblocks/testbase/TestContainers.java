package buildingblocks.testbase;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

public final class TestContainers {
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_pass")
            .withExposedPorts(5432);

    public static final RabbitMQContainer rabbitMq = new RabbitMQContainer("rabbitmq:management")
            .withExposedPorts(5672, 15672);

    public static final MongoDBContainer mongoDb = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017);

    private static boolean initialized = false;

    public static synchronized void initializeContainersOnce() {
        if (!initialized) {
            postgres.start();
            rabbitMq.start();
            mongoDb.start();
            initialized = true;

            Runtime.getRuntime().addShutdownHook(new Thread(TestContainers::stopContainers));
        }
    }

    public static synchronized void stopContainers() {
        postgres.stop();
        rabbitMq.stop();
        mongoDb.stop();
    }
}