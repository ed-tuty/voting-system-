package com.sicredi.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUserCpf extends ApiException {

  public InvalidUserCpf(String cpf) {
    super("Invalid CPF " + cpf);
  }
}
