package com.sicredi.test.controller.votingsession;

import com.sicredi.test.exception.NotFoundException;
import com.sicredi.test.model.VotingSession;
import com.sicredi.test.service.votingsession.VotingSessionService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/voting-session")
public class VotingSessionController {

  private final VotingSessionService service;
  private final VotingSessionMapper mapper;

  @GetMapping
  public Flux<VotingSession> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Mono<VotingSession> findById(@PathVariable("id") String id) {
    return service.findById(id)
        .switchIfEmpty(
            Mono.error(new NotFoundException("Not Found Voting Session with id: " + id)));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<VotingSession> create(@Valid @RequestBody VotingSessionRequest request) {
    return service.create(mapper.toEntity(request), request.getSessionTime());
  }

  @PostMapping("/{votingSessionId}/addVote")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<VotingSession> addVote(@Valid @RequestBody VoteRequest request,
      @PathVariable("votingSessionId") String votingSessionId) {
    return service.findById(votingSessionId)
        .switchIfEmpty(Mono.error(
            new NotFoundException("Not Found Voting Session with id: " + votingSessionId)))
        .flatMap(votingSession -> service.addVote(votingSession, mapper.toEntity(request),
            request.getCpf()));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> delete(@PathVariable("id") String id) {
    return service.findById(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Not Found Agenda with id: " + id)))
        .flatMap(service::delete);
  }
}
