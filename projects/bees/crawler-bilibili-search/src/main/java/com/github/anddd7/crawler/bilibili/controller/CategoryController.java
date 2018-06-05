package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.crawler.bilibili.repository.CategoryRepository;
import com.github.anddd7.model.bilibili.entity.Category;
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
@RequestMapping(value = "/${api.prefix}/${api.version}/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryController(final CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  public ResponseEntity<List<Category>> getCategories() {
    return ResponseEntity.ok(categoryRepository.getCategories());
  }
}