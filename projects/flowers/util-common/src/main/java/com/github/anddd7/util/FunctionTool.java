package com.github.anddd7.util;

import com.google.common.base.Strings;
import java.util.function.Consumer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FunctionTool {

  public static void isNotBlankThen(String string, Consumer<String> consumer) {
    if (!Strings.isNullOrEmpty(string)) {
      consumer.accept(string);
    }
  }
}
