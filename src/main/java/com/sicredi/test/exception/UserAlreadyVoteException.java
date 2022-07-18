package com.sicredi.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyVoteException extends ApiException {

  public UserAlreadyVoteException(Integer id) {
    super("User with id " + id + " already voted");
  }
}
