package kr.mashup.branding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
class SwaggerConfiguration {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api Document")
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.mashup.branding.ui"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}