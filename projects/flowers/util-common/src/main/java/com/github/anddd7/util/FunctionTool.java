package com.github.anddd7.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class FunctionTool {

  public static <T> Comparator<T> comparingIntDesc(ToIntFunction<? super T> keyExtractor) {
    Objects.requireNonNull(keyExtractor);
    return (Comparator<T> & Serializable)
        (c1, c2) -> Math.negateExact(
            Integer.compare(
                keyExtractor.applyAsInt(c1),
                keyExtractor.applyAsInt(c2)
            )
        );
  }
}
