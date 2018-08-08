package com.github.anddd7.dashboard.bilibili.controller;

import com.github.anddd7.dashboard.bilibili.client.AnalysisApiClient;
import com.github.anddd7.model.bilibili.entity.PublishedRecord;
import com.github.anddd7.model.bilibili.entity.Top10OfPublishedData;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/result", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ResultDataController {

  private AnalysisApiClient analysisApiClient;

  @Autowired
  public ResultDataController(
      AnalysisApiClient analysisApiClient) {
    this.analysisApiClient = analysisApiClient;
  }

  @GetMapping("/published")
  public ResponseEntity<Top10OfPublishedData> getTop10OfPublishedData() {
    List<PublishedRecord> sortByCategory = analysisApiClient.getCalculatedPublishedRecord();
    return ResponseEntity.ok(new Top10OfPublishedData(sortByCategory));
  }
}
