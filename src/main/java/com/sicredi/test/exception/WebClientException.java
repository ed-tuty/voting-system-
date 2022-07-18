package com.sicredi.test.exception;

public class WebClientException  extends ApiException {

  public WebClientException(String message) {
    super("Web Client Error: " + message);
  }
}