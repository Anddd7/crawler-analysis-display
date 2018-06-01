package com.github.anddd7.crawler.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient
@EnableSwagger2
@EnableScheduling
@ComponentScan("com.github.anddd7")
@SpringBootApplication
public class BilibiliSearchApiAppConfig {

  public static void main(String[] args) {
    SpringApplication.run(BilibiliSearchApiAppConfig.class);
  }
}
