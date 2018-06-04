package com.github.anddd7.boot.configuration;

import com.github.anddd7.boot.factory.SwaggerDocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * swagger注解配置, 通过配置文件管理参数
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty({"swagger.enabled"})
public class SwaggerConfiguration {

  @Value("${api.version}")
  private String version;

  @Value("${spring.application.name}")
  private String name;

  @Value("${spring.application.description}")
  private String description;

  @Value("${info.contact.author}")
  private String author;

  @Value("${info.contact.url}")
  private String url;

  @Value("${info.contact.email}")
  private String email;

  @Value("${swagger.protocol:https}")
  private String protocol;

  @Bean
  @ConditionalOnMissingBean
  public Docket createDocket() {
    return SwaggerDocketFactory.createDocket(name, metadata(), protocol);
  }

  private ApiInfo metadata() {
    Contact contact = new Contact(author, url, email);
    return new ApiInfoBuilder()
        .title(name)
        .description(description)
        .contact(contact)
        .version(version)
        .build();
  }
}
