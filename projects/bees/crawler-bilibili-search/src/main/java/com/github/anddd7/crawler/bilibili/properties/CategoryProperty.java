package com.github.anddd7.crawler.bilibili.properties;


import static com.github.anddd7.util.JacksonJsonTool.getYamlMapper;

import com.github.anddd7.model.bilibili.entity.Category;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProperty {

  private static final String FILE_NAME = "categories.yml";
  @Getter
  @Setter
  private List<Category> categories;

  public static CategoryProperty load() {
    ClassPathResource resource = new ClassPathResource(FILE_NAME);
    try {
      return getYamlMapper().readValue(resource.getInputStream(), CategoryProperty.class);
    } catch (IOException e) {
      log.error("Not find categories file: {}", FILE_NAME);
      log.error("{}", e);
    }
    return new CategoryProperty(Collections.emptyList());
  }
}

