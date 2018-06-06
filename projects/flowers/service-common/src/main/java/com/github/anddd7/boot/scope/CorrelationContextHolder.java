package com.github.anddd7.boot.scope;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CorrelationContextHolder {

  private static final CorrelationContextLocal LOCAL = new CorrelationContextLocal();

  public static CorrelationContext get() {
    return LOCAL.get();
  }

  private static class CorrelationContextLocal extends ThreadLocal<CorrelationContext> {

    @Override
    protected CorrelationContext initialValue() {
      return new CorrelationContext();
    }
  }
}
