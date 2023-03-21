package es.um.atica.faker;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.media.MediaType;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;

@OpenAPIDefinition
@Configuration
public class FakerOpenAPI {
    
    @Autowired
    private ObjectMapper objectMapper;

    private Map<String,String> errorResponses = new HashMap<>();

    private ApiResponse buildApiResponse(String desc, String... errorIDs) {
        MediaType mediaType = new MediaType();
        for (String id: errorIDs) {
            mediaType.addExamples(id,new Example().value(errorResponses.get(id)));
        }
        return new ApiResponse().content(
            new Content().addMediaType(
                org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                mediaType)
            ).description(desc);
    }

    /*@Bean
    public GroupedOpenApi usersApis() {
        return GroupedOpenApi.builder()
            .group("User Endpoints")
            .pathsToMatch(new String[]{"/faker/v1/**"})
            .build();
    }*/

    @Bean
    public OpenAPI baseOpenAPI() {

        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("openapi/responses.json");
            errorResponses = objectMapper.readValue(in, new TypeReference<Map<String,Object>>(){})
                    .entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                                  e -> {
                                    try {
                                        return objectMapper.writeValueAsString(e.getValue());
                                    } catch (JsonProcessingException e1) { }
                                    return e.getValue().toString();
                                }));
        } catch (Exception ex) {
            // Failed
        }
        
        Components components = new Components();
        components.addResponses("unauthorized", buildApiResponse("Unauthorized!","401"));
        components.addResponses("forbidden_get", buildApiResponse("Forbidden!","403_04"));
        components.addResponses("forbidden_put", buildApiResponse("Forbidden!","403_01"));
        components.addResponses("forbidden_post", buildApiResponse("Forbidden!","403_02"));
        components.addResponses("forbidden_delete", buildApiResponse("Forbidden!","403_03"));
        components.addResponses("notfound", buildApiResponse("Not Found!","404"));
        components.addResponses("badrequest_put", buildApiResponse("Not Found!","400_01","400_02"));
        components.addResponses("badrequest_post", buildApiResponse("Not Found!","400_03","400_04"));
        components.addResponses("conflict", buildApiResponse("Conflict!","409"));
        components.addResponses("ok_user", buildApiResponse("Success!","200_01"));
        components.addResponses("ok_users", buildApiResponse("Success!","200_02"));

        return new OpenAPI()
            .components(components)
            .info(new Info()
                .title("Faker OpenAPI Doc")
                .version("1.0.0")
                .description("This is the faker api documentation")
            );
    }

}
