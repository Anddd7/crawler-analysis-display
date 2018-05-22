package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.boot.exception.NotSupportedException;
import com.github.anddd7.boot.utils.constant.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${api.version}/search", produces = Constants.CONTENT_TYPE)
public class SearchController {

  @GetMapping("/today")
  public ResponseEntity<String> getToday() {
    return new NotSupportedException().toResponse();
  }

}
