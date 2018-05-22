package com.github.anddd7.crawler.bilibili;

import com.github.anddd7.boot.configuration.JacksonConfiguration;
import com.github.anddd7.boot.configuration.RestTemplateConfiguration;
import com.github.anddd7.boot.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@Import({JacksonConfiguration.class, SwaggerConfiguration.class, RestTemplateConfiguration.class})
public class AppConfig {

  public static void main(String[] args) {
    SpringApplication.run(AppConfig.class);
  }
}
