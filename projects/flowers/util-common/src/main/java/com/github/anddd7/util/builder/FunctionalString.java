package com.github.anddd7.util.builder;

import com.google.common.base.Strings;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class FunctionalString {

  private final String target;
  private final boolean isBlank;

  private FunctionalString(String target) {
    this.target = target;
    this.isBlank = Strings.isNullOrEmpty(target);
  }

  public static FunctionalString of(String string) {
    return new FunctionalString(string);
  }

  public void then(Consumer<String> consumer) {
    if (!isBlank) {
      consumer.accept(target);
    }
  }

  public FunctionalString or(Supplier<String> supplier) {
    return isBlank ? new FunctionalString(supplier.get()) : this;
  }

  public FunctionalString or(String string) {
    return isBlank ? new FunctionalString(string) : this;
  }

  @Override
  public String toString() {
    return isBlank ? null : target;
  }
}
