package buildingblocks.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("API v1")
                .pathsToMatch("/api/v1/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    return operation;
                })
                .build();
    }

    @Bean
    public Info apiInfo() {
        return new Info()
                .title("My Spring Boot API")
                .description("This is a sample API documentation")
                .version("1.0.0");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // Define the security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .description("Provide the JWT token to access these APIs");

        // Add the security scheme to OpenAPI
        return new OpenAPI()
                .info(new Info()
                        .title("My Spring Boot API")
                        .description("This is a sample API documentation")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("BearerToken"))
                .components(new Components().addSecuritySchemes("BearerToken", securityScheme));
    }
}
