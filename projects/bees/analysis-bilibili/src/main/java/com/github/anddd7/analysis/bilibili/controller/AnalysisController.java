package com.github.anddd7.analysis.bilibili.controller;

import com.github.anddd7.analysis.bilibili.service.CategoryAnalysisService;
import com.github.anddd7.model.bilibili.entity.PublishedRecord;
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
@RequestMapping(value = "/${api.prefix}/${api.version}/analysis", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AnalysisController {

  private final CategoryAnalysisService categoryAnalysisService;

  @Autowired
  public AnalysisController(
      CategoryAnalysisService categoryAnalysisService) {
    this.categoryAnalysisService = categoryAnalysisService;
  }


  @GetMapping("/published")
  public ResponseEntity<List<PublishedRecord>> getCalculatedPublishedRecord() {
    return ResponseEntity.ok(categoryAnalysisService.getCalculatedPublishedRecord());
  }
}
