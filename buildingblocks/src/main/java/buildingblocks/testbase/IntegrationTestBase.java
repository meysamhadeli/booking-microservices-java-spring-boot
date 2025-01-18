package buildingblocks.testbase;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @Autowired
    private ApplicationContext applicationContext;

    protected TestFixture fixture;

    @PostConstruct
    public void setupFixture() {
        this.fixture = new TestFixture(applicationContext);
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
        fixture.cleanupJpa();
        fixture.cleanupMongo();
        fixture.cleanupRabbitmq();
    }
}