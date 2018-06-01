package com.github.anddd7.analysis.bilibili.controller;

import com.github.anddd7.analysis.bilibili.client.SearchApiClient;
import com.github.anddd7.model.bilibili.entity.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${api.version}/analysis", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AnalysisController {

  private final SearchApiClient searchApiClient;

  @Autowired
  public AnalysisController(final SearchApiClient searchApiClient) {
    this.searchApiClient = searchApiClient;
  }

  @GetMapping
  public ResponseEntity<List<Category>> getCategories() {
    return searchApiClient.getCategories();
  }
}
