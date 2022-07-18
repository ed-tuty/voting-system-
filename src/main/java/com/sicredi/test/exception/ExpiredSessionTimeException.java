package com.sicredi.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExpiredSessionTimeException extends ApiException {

  public ExpiredSessionTimeException(String id) {
    super("Expired or no open voting session for this agenda with id " + id);
  }
}
