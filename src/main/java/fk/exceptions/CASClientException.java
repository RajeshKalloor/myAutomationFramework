package fk.exceptions;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class CASClientException extends RuntimeException {
  public CASClientException() {
  }

  public CASClientException(Throwable cause) {
    super(cause);
  }

  public CASClientException(String message) {
    super(message);
  }

  public CASClientException(String message, Throwable cause) {
    super(message, cause);
  }

}
