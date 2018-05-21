package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.CollectionTool;
import com.github.anddd7.crawler.bilibili.utils.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anddd7
 */
@RestController
@RequestMapping(value = "/${api.version}/hello", consumes = Constants.CONTENT_TYPE, produces = Constants.CONTENT_TYPE)
public class HelloController {

  private static final String HELLO = "hello";

  @GetMapping
  public String hello() {
    return HELLO;
  }

  @GetMapping("/welcome")
  public String welcome(String[] messages) {
    return CollectionTool.join(" ", messages);
  }
}
