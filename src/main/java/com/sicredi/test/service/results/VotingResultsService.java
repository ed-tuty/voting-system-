package com.sicredi.test.service.results;

import com.sicredi.test.kafka.producer.ResultProducer;
import com.sicredi.test.model.ResultDto;
import com.sicredi.test.repository.VotingResultsRepository;
import com.sicredi.test.service.votingsession.VotingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class VotingResultsService {

  private final VotingResultsRepository repository;
  private final VotingSessionService votingSessionService;
  private final ResultProducer producer;

  public Flux<ResultDto> getResult(String agendaId) {
    return repository.getResult(agendaId);
  }

  public Mono<Void> closeVotingSessionsAndEmitResult() {
    return votingSessionService.findSessionsToClose()
        .flatMap(agenda -> {
          return votingSessionService.closeVotingSession(agenda);
        }).flatMap(votingSession -> {
          return getResult(votingSession.getAgendaId().toString());
        }).doOnNext(producer::sendMessage).then();
  }
}
