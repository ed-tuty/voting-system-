package com.sicredi.test.service.agenda;

import com.sicredi.test.model.Agenda;
import com.sicredi.test.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class AgendaService {

  private final AgendaRepository repository;

  public Flux<Agenda> findAll() {
    return repository.findAll();
  }

  public Mono<Agenda> findById(String id) {
    return repository.findById(id);
  }

  public Mono<Agenda> create(Agenda agenda) {
    return Mono.just(agenda)
        .flatMap(repository::save);
  }

  public Mono<Void> delete(Agenda agenda) {
    return repository.delete(agenda);
  }

  public Mono<Boolean> existsById(String id) {
    return repository.existsById(id);
  }
}
