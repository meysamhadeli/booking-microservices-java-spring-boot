package buildingblocks.testbase;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(authorities = "ADMIN")
public abstract class TestBase {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    protected MockMvc mockMvc;

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
    static void initializeContainers() {
        TestContainers.initializeContainersOnce();
    }

    @AfterEach
    public void cleanDatabase() {
        fixture.cleanupJpa();
        fixture.cleanupMongo();
        fixture.cleanupRabbitmq();
    }
}