package github.eddy.bigdata.bilibili.beans;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnCheckException extends RuntimeException {

  public UnCheckException() {
  }

  public UnCheckException(String message) {
    super(message);
  }

  public UnCheckException(String message, Throwable cause) {
    super(message, cause);
  }
}
