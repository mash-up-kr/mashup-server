package kr.mashup.branding.config.swagger;

import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Profile("swagger")
@ConditionalOnWebApplication
@Configuration
class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Api Document")
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .directModelSubstitute(Pageable.class, SwaggerPageableRequest.class)
            .securitySchemes(Collections.singletonList(apiKey()))
            .securityContexts(Collections.singletonList(securityContext()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(Collections.singletonList(securityReference()))
            .forPaths(PathSelectors.regex("(?!/api/v1/applicants/login).*"))
            .build();
    }

    private SecurityReference securityReference() {
        return SecurityReference.builder()
            .reference("Bearer {accessToken}")
            .scopes(new AuthorizationScope[] {new AuthorizationScope("global", "access All")})
            .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer {accessToken}", "Authorization", "header");
    }
}