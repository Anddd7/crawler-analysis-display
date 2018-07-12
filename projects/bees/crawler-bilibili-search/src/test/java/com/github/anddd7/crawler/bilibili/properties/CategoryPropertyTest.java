package com.github.anddd7.crawler.bilibili.properties;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class CategoryPropertyTest {

  @Test
  public void load() throws IOException {
    Assert.assertEquals(67, CategoryProperty.load().getCategories().size());
  }
}