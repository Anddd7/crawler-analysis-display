package com.github.anddd7.util;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JacksonJsonTool {

  private static final ObjectMapper MAPPER = buildMapper();
  private static final ObjectMapper DEBUG_MAPPER = buildDebugMapper();
  private static final ObjectMapper YAML_MAPPER = buildYamlMapper();

  private static ObjectMapper buildMapper() {
    return new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .setSerializationInclusion(NON_EMPTY)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  private static ObjectMapper buildDebugMapper() {
    return buildMapper().enable(SerializationFeature.INDENT_OUTPUT);
  }

  private static ObjectMapper buildYamlMapper() {
    return new ObjectMapper(new YAMLFactory());
  }

  public static ObjectMapper getDefaultMapper() {
    return MAPPER;
  }

  public static ObjectMapper getDebugMapper() {
    return DEBUG_MAPPER;
  }

  public static ObjectMapper getYamlMapper() {
    return YAML_MAPPER;
  }
}
