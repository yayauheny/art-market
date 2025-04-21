package by.yayauheny.http.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
public class NoSuchEntityException extends RuntimeException {

  public NoSuchEntityException() {
  }

  public NoSuchEntityException(String message) {
    super(message);
  }
}