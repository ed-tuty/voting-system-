package com.sicredi.test.schedule;

import com.sicredi.test.service.agenda.AgendaService;
import com.sicredi.test.service.results.VotingResultsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VoteSessionScheduling {

  private final VotingResultsService votingResultsService;


  @Scheduled(cron = "*/10 * * * * *")
  @Async
  public void verifyVotingSessionAndEmitResult() {
    log.info("start voting result schedule");
    votingResultsService.closeVotingSessionsAndEmitResult().subscribe();
  }
}
