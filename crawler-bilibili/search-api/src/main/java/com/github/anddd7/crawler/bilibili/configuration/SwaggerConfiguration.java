package com.github.anddd7.crawler.bilibili.configuration;

import static org.apache.logging.log4j.util.Strings.EMPTY;

import com.github.anddd7.crawler.bilibili.utils.swagger.SwaggerDocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
    private static final String CONTROLLER_PACKAGE = "com.thoughtworks.workshop.ddd";

    @Value("${info.version}")
    private String version;

    @Value("${info.app.name}")
    private String name;

    @Value("${info.app.description}")
    private String description;

    @Value("${info.contact.author}")
    private String author;

    @Value("${info.contact.email}")
    private String email;

    @Value("${info.app.protocol:https}")
    private String protocol;

    @Bean
    public Docket createDocket() {
        return SwaggerDocketFactory.createDocket(CONTROLLER_PACKAGE, name, apiInfo(), protocol);
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(author, EMPTY, email);
        return new ApiInfoBuilder()
                .title(name)
                .description(description)
                .contact(contact)
                .version(version)
                .build();

    }

}
