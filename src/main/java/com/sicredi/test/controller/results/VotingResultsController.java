package com.sicredi.test.controller.results;

import com.sicredi.test.model.ResultDto;
import com.sicredi.test.service.results.VotingResultsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/results")
public class VotingResultsController {

  private final VotingResultsService service;

  @GetMapping()
  public Flux<ResultDto> getAgendaVotes(@RequestParam("agendaId") String agendaId) {
    return service.getResult(agendaId);
  }
}
