package es.um.atica.faker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
@ComponentScan(basePackages = {"es.um.atica.faker","es.um.atica.shared"})
public class FakerApplication {

	@Bean
	public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

}
