package com.sicredi.test.controller.votingsession.agenda;

import com.sicredi.test.exception.NotFoundException;
import com.sicredi.test.model.Agenda;
import com.sicredi.test.service.agenda.AgendaService;
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
@RequestMapping("/v1/agenda")
public class AgendaController {

  private final AgendaService service;
  private final AgendaMapper mapper;

  @GetMapping
  public Flux<Agenda> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Agenda> findById(@PathVariable("id") String id) {
    return service.findById(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Not Found Agenda with id: " + id)));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Agenda> create(@Valid @RequestBody AgendaRequest request) {
    return service.create(mapper.toEntity(request));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> delete(@PathVariable("id") String id) {
    return service.findById(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Not Found Agenda with id: " + id)))
        .flatMap(service::delete);
  }
}
