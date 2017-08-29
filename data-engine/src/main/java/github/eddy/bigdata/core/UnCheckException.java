package github.eddy.bigdata.core;

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
