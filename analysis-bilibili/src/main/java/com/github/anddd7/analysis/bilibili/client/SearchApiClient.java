package com.github.anddd7.analysis.bilibili.client;


import com.github.anddd7.boot.properties.RemoteServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 对 search api 的调用
 */
@Service
public class SearchApiClient {

  private static final String ENDPOINT = "/search";
  private static final String TODAY = "/today";


  private final RestTemplate restTemplate;
  private final RemoteServerProperties remoteServerProperties;

  @Autowired
  public SearchApiClient(final RestTemplate restTemplate,
      final RemoteServerProperties remoteServerProperties) {
    this.restTemplate = restTemplate;
    this.remoteServerProperties = remoteServerProperties;
  }


  public void searchToday() {
    //TODO
  }

  private String getURI() {
    return remoteServerProperties.getURI() + ENDPOINT;
  }
}

