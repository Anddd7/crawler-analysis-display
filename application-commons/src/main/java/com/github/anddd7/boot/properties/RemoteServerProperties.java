package com.github.anddd7.boot.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "remote")
@Setter
@Getter
public class RemoteServerProperties {

  private String baseURL;
  private String contextURL;

  public String getURI() {
    return baseURL + contextURL;
  }
}
