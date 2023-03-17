package es.um.atica.faker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
class FakerApplicationTests {

	@Test
	void contextLoads() {
	}

}
