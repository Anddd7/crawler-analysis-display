package com.github.anddd7.boot.scope;

import lombok.experimental.UtilityClass;

/**
 * Store correlation-id in Thread Local
 * Set if any request in
 * Get if any response out
 */
@UtilityClass
public class CorrelationContextHolder {

  /**
   * ThreadLocal object, only exist 1 instance in a thread
   */
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
