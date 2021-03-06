package com.example.demo.book.configuration;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket booksApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .paths(PathSelectors.regex("/books.*"))
            .build()
            .protocols(Set.of("http", "https"))
            .pathMapping("/")
            .useDefaultResponseMessages(false);
    }
}
