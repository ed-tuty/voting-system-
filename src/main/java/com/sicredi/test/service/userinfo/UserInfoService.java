package com.sicredi.test.service.userinfo;

import com.sicredi.test.exception.InvalidUserCpf;
import com.sicredi.test.exception.WebClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserInfoService {

  public Mono<VoteStatusWebClientResponse> getVoteStatusEnum(String cpf) {
    return WebClient.create()
        .get()
        .uri(String.format("https://user-info.herokuapp.com/users/%s", cpf))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(HttpStatus::isError, response -> {
          if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new InvalidUserCpf(cpf);
          } else {
            throw new WebClientException(response.statusCode().toString());
          }
        })
        .bodyToMono(VoteStatusWebClientResponse.class);
  }
}
