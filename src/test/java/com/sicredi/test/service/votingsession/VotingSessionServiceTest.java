package com.sicredi.test.service.votingsession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sicredi.test.model.Agenda;
import com.sicredi.test.model.VotingSession;
import com.sicredi.test.repository.VotingSessionRepository;
import com.sicredi.test.service.agenda.AgendaService;
import com.sicredi.test.service.userinfo.UserInfoService;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class VotingSessionServiceTest {

  private VotingSessionService service;
  private VotingSessionRepository repository;
  private AgendaService agendaService;
  private UserInfoService userInfoService;

  @BeforeEach
  void setup() {
    repository = mock(VotingSessionRepository.class);
    agendaService = mock(AgendaService.class);
    userInfoService = mock(UserInfoService.class);

    service = new VotingSessionService(repository, agendaService, userInfoService);
  }

  @Test
  void create() {
    Agenda agenda = buildAgenda();
    VotingSession votingSession = buildVotingSession();
    votingSession.setAgendaId(new ObjectId(agenda.getId()));

    when(agendaService.existsById(agenda.getId())).thenReturn(Mono.just(true));
    when(repository.save(votingSession)).thenReturn(Mono.just(votingSession));

    service.create(votingSession, 1L);

    StepVerifier.create(service.create(votingSession, 1L))
        .consumeNextWith(response -> {
          assertEquals(response.getId(), votingSession.getId());
          assertEquals(response.getAgendaId(), votingSession.getAgendaId());
          assertFalse(response.getClosed());
        }).verifyComplete();
  }

  @Test
  void shouldThrownErronOnCreateWithNoAgenda() {
    Agenda agenda = buildAgenda();
    VotingSession votingSession = buildVotingSession();
    votingSession.setAgendaId(new ObjectId(agenda.getId()));

    when(agendaService.existsById(agenda.getId())).thenReturn(Mono.just(false));
    when(repository.save(votingSession)).thenReturn(Mono.just(votingSession));

    service.create(votingSession, 1L);

    StepVerifier.create(service.create(votingSession, 1L))
        .expectError();
  }

  private Agenda buildAgenda() {
    return Agenda.builder()
        .id("62d4a302992e9523b3cba660")
        .name("a name ")
        .description("a description")
        .build();
  }

  private VotingSession buildVotingSession() {
    return VotingSession.builder()
        .closed(false)
        .id("62d497a386ef232053bbb974")
        .startDate(LocalDateTime.now())
        .endDate(LocalDateTime.now().plusMinutes(1L))
        .build();
  }
}