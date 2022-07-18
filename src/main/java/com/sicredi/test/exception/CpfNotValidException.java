package com.sicredi.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CpfNotValidException extends ApiException {

  public CpfNotValidException(String id) {
    super("cpf not valid " + id);
  }
}
