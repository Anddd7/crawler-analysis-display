package com.github.anddd7.util.builder;

import com.google.common.base.Strings;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class OptionalString {

  private final String target;
  private final boolean isBlank;

  private OptionalString(String target) {
    this.target = target;
    this.isBlank = Strings.isNullOrEmpty(target);
  }

  public static OptionalString of(String string) {
    return new OptionalString(string);
  }

  public void ifNotBlank(Consumer<String> consumer) {
    if (!isBlank) {
      consumer.accept(target);
    }
  }

  public OptionalString or(Supplier<String> supplier) {
    return isBlank ? new OptionalString(supplier.get()) : this;
  }

  public OptionalString or(String string) {
    return isBlank ? new OptionalString(string) : this;
  }

  public String orElse(Supplier<String> supplier) {
    return isBlank ? supplier.get() : target;
  }

  public String orElse(String string) {
    return isBlank ? string : target;
  }

  public String getTarget() {
    return target;
  }

  public boolean isBlank() {
    return isBlank;
  }

  @Override
  public String toString() {
    return isBlank ? null : target;
  }
}
