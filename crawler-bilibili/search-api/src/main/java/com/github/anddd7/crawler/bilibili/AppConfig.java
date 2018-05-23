package com.github.anddd7.crawler.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.github.anddd7")
@EnableSwagger2
@EnableScheduling
public class AppConfig {

  public static void main(String[] args) {
    SpringApplication.run(AppConfig.class);
  }
}
