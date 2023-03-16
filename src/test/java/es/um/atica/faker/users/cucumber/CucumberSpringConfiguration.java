package es.um.atica.faker.users.cucumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RecordApplicationEvents
public class CucumberSpringConfiguration {

    @Autowired
    protected MockMvc mvc;
    @Autowired 
    protected ApplicationEvents applicationEvents;
    @Autowired
    protected ObjectMapper objectMapper;

}
