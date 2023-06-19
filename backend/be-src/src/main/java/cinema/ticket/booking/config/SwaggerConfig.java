package cinema.ticket.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    
    @Bean
	public OpenAPI baseOpenAPI() {
		return new OpenAPI().info(
                            new Info()
                                .title("Rest API Guidlines")
                                .description("This is a document to demonstrate how backend work.")
                                .version("1.0.0")
                            );
	}
}
