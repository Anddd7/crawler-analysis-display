package com.github.anddd7.crawler.bilibili.repository;

import com.github.anddd7.crawler.bilibili.properties.CategoryProperty;
import com.github.anddd7.model.bilibili.entity.Category;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

  private CategoryProperty categories;

  public CategoryRepository() {
    this.categories = CategoryProperty.load();
  }

  public List<Category> getCategories() {
    return Collections.unmodifiableList(categories.getCategories());
  }
}
