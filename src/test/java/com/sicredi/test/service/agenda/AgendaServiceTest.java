package com.sicredi.test.service.agenda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sicredi.test.model.Agenda;
import com.sicredi.test.repository.AgendaRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class AgendaServiceTest {

  private AgendaService service;
  private AgendaRepository repository;

  @BeforeEach
  void setup() {
    repository = mock(AgendaRepository.class);
    service = new AgendaService(repository);
  }

  @Test
  void findAll() {
    List<Agenda> agendaList = Collections.singletonList(buildAgenda());

    when(repository.findAll()).thenReturn(Flux.fromIterable(agendaList));

    StepVerifier.create(service.findAll())
        .consumeNextWith(agenda -> {
          assertEquals(agenda.getId(), agendaList.get(0).getId());
          assertEquals(agenda.getDescription(), agendaList.get(0).getDescription());
          assertEquals(agenda.getName(), agendaList.get(0).getName());
        }).verifyComplete();
  }

  @Test
  void findById() {
    Agenda agenda = buildAgenda();

    when(repository.findById(agenda.getId())).thenReturn(Mono.just(agenda));

    StepVerifier.create(service.findById(agenda.getId()))
        .consumeNextWith(agendaToValidate -> {
          assertEquals(agendaToValidate.getId(), agenda.getId());
          assertEquals(agendaToValidate.getDescription(), agenda.getDescription());
          assertEquals(agendaToValidate.getName(), agenda.getName());
        }).verifyComplete();
  }

  @Test
  void create() {
    Agenda agenda = buildAgenda();

    when(repository.save(agenda)).thenReturn(Mono.just(agenda));

    StepVerifier.create(service.create(agenda))
        .consumeNextWith(agendaToValidate -> {
          assertEquals(agendaToValidate.getId(), agenda.getId());
          assertEquals(agendaToValidate.getDescription(), agenda.getDescription());
          assertEquals(agendaToValidate.getName(), agenda.getName());
        }).verifyComplete();
  }

  @Test
  void delete() {
    Agenda agenda = buildAgenda();

    when(repository.delete(agenda)).thenReturn(Mono.empty());

    StepVerifier.create(service.delete(agenda))
        .verifyComplete();
  }

  private Agenda buildAgenda() {
    return Agenda.builder()
        .id("asdfasdf")
        .name("a name ")
        .description("a description")
        .build();
  }
}