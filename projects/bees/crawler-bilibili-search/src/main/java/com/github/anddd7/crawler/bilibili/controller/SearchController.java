package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.boot.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${api.version}/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SearchController {

  @GetMapping("/today")
  public ResponseEntity getToday() {
    throw new ResourceNotFoundException("Not implement");
  }

}
