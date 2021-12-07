package com.metodo.bookstoremanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.metodo.bookstoremanager";
    private static final String API_TITLE = "Bookstore Manager API";
    private static final String API_DESCRIPTION = "Bookstore Manager Professional API develop by Method.inc";
    private static final String API_VERSION = "1.0.0.";
    private static final String CONTACT_NAME = "Alef Meira";
    private static final String CONTACT_GITHUB = "https://github.com/AlefMeira";
    private static final String CONTACT_EMAIL = "alef.test@gmail.com";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
                .build();
    }
}
