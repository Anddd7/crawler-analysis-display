package github.eddy.bigdata.core.beans;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KnownException extends RuntimeException {

  public KnownException() {
  }

  public KnownException(String message) {
    super(message);
  }

  public KnownException(String message, Throwable cause) {
    super(message, cause);
  }
}
