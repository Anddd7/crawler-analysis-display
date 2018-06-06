package com.github.anddd7.boot.scope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CorrelationContext {

  public static final String CORRELATION_ID_HEADER = "Correlation-Id";
  public static final String SESSION_ID_HEADER = "Session-Id";
  private String correlationId;
  private String sessionId;
}
