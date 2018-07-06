package com.github.anddd7.boot.scope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Correlation Tracking message
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CorrelationContext {

  public static final String HEADER_CORRELATION_ID = "Correlation-Id".toLowerCase();
  public static final String HEADER_SESSION_ID = "Session-Id".toLowerCase();

  private String correlationId;
  private String sessionId;
}
