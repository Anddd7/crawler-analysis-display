package com.github.anddd7.crawler.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class BilibiliSearchApiAppConfig {

  public static void main(String[] args) {
    SpringApplication.run(BilibiliSearchApiAppConfig.class);
  }
}
