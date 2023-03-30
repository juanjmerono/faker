package es.um.atica.faker.users.cucumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.fasterxml.jackson.databind.ObjectMapper;
 
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(initializers = {CucumberSpringConfiguration.Initializer.class})
public class CucumberSpringConfiguration {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    private static final int RABBITMQ_DEFAULT_PORT = 5672;
    private static final int RABBITMQ_DEFAULT_HTTP_PORT = 15672;

    @Container
    static RabbitMQContainer rabbitMQ;

    static {
        rabbitMQ = new RabbitMQContainer("rabbitmq:3.7.25-management-alpine")
            .withExposedPorts(RABBITMQ_DEFAULT_PORT,RABBITMQ_DEFAULT_HTTP_PORT)
            .waitingFor(Wait.forListeningPort());
        rabbitMQ.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                "spring.cloud.function.definition=eventProducer;createdConsumer;createdConsumer2;allConsumer;deletedConsumer",
                "spring.rabbitmq.host=" + rabbitMQ.getHost(),
                "spring.rabbitmq.port=" + rabbitMQ.getMappedPort(RABBITMQ_DEFAULT_PORT),
                "spring.rabbitmq.username=" + rabbitMQ.getAdminUsername(),
                "spring.rabbitmq.password=" + rabbitMQ.getAdminPassword());
        }
    }

}
