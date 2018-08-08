package com.github.anddd7.util.builder;

import org.junit.Assert;
import org.junit.Test;

public class OptionalStringTest {

  @Test
  public void optionalString() {
    String string = "string";
    String emptyString = "";
    String nullString = null;

    Assert.assertFalse(OptionalString.of(string).isBlank());
    Assert.assertTrue(OptionalString.of(emptyString).isBlank());
    Assert.assertTrue(OptionalString.of(nullString).isBlank());

    Assert.assertFalse(OptionalString.of(emptyString).or(() -> "emptyString").isBlank());
    Assert.assertFalse(OptionalString.of(nullString).or("nullString").isBlank());

    Assert.assertEquals("emptyString", OptionalString.of(emptyString).orElse(() -> "emptyString"));
    Assert.assertEquals("nullString", OptionalString.of(nullString).orElse("nullString"));

    StringBuilder sb = new StringBuilder();
    OptionalString.of(string).ifNotBlank(sb::append);
    OptionalString.of(emptyString).ifNotBlank(sb::append);
    OptionalString.of(nullString).ifNotBlank(sb::append);
    Assert.assertEquals("string", sb.toString());
  }
}