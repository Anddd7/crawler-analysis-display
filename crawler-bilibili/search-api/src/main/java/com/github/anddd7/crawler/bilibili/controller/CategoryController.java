package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.boot.utils.constant.Constants;
import com.github.anddd7.crawler.bilibili.entity.Category;
import com.github.anddd7.crawler.bilibili.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping(value = "/${api.version}/repository", produces = Constants.CONTENT_TYPE)
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