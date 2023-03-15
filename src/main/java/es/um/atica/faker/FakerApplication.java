package es.um.atica.faker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"es.um.atica.faker","es.um.atica.shared"})
public class FakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

}
