package by.yayauheny.http.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Resource already exists")
public class EntityAlreadyExists extends RuntimeException {

  public EntityAlreadyExists() {
  }

  public EntityAlreadyExists(String message) {
    super(message);
  }
}