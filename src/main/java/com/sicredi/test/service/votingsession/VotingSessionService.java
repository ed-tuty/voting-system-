package com.sicredi.test.service.votingsession;

import com.sicredi.test.exception.CpfNotValidException;
import com.sicredi.test.exception.ExpiredSessionTimeException;
import com.sicredi.test.exception.NotFoundException;
import com.sicredi.test.exception.UserAlreadyVoteException;
import com.sicredi.test.model.Vote;
import com.sicredi.test.model.VotingSession;
import com.sicredi.test.repository.VotingSessionRepository;
import com.sicredi.test.service.agenda.AgendaService;
import com.sicredi.test.service.userinfo.UserInfoService;
import com.sicredi.test.service.userinfo.VoteStatusEnum;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class VotingSessionService {

  private final VotingSessionRepository repository;
  private final AgendaService agendaService;
  private final UserInfoService userInfoService;

  public Mono<VotingSession> findById(String id) {
    return repository.findById(id);
  }

  public Flux<VotingSession> findAll() {
    return repository.findAll();
  }

  public Mono<Boolean> exitsById(String id) {
    return repository.existsById(id);
  }

  public Mono<VotingSession> create(VotingSession votingSession, Long sessionTime) {
    return validateVotingSessionCreate(votingSession)
        .thenReturn(votingSession)
        .flatMap(votingSessionToSave -> {
          LocalDateTime startDate = LocalDateTime.now();
          LocalDateTime endDate = startDate.plusMinutes(sessionTime != null ? sessionTime : 1L);

          votingSessionToSave.setStartDate(startDate);
          votingSessionToSave.setEndDate(endDate);
          return repository.save(votingSessionToSave);
        });
  }

  public Mono<Void> delete(VotingSession votingSession) {
    return repository.delete(votingSession);
  }

  public Mono<Boolean> isSessionOpen(String votingSessionId) {
    return repository.findById(votingSessionId)
        .map(votingSession -> {
          return !votingSession.getClosed();
        });
  }

  public Flux<VotingSession> findSessionsToClose() {
    return repository.findAllByClosedAndEndDateLessThan(false, LocalDateTime.now());
  }

  public Mono<VotingSession> closeVotingSession(VotingSession votingSession) {
    return Mono.just(votingSession)
        .flatMap(votingSessionToClose -> {
          votingSessionToClose.setClosed(true);
          return repository.save(votingSessionToClose);
        });
  }

  public Flux<VotingSession> findAllByAgendaId(String agendaId) {
    return repository.findAllByAgendaId(agendaId);
  }

  public Mono<VotingSession> addVote(VotingSession votingSession, Vote vote, String cpf) {
    return validateAddVote(votingSession, vote, cpf)
        .thenReturn(votingSession)
        .flatMap(votingSessionToSave -> {
          List<Vote> votes = votingSessionToSave.getVotes();
          votes.add(vote);
          votingSessionToSave.setVotes(votes);
          return repository.save(votingSessionToSave);
        });
  }

  private Mono<Void> validateAddVote(VotingSession votingSession, Vote vote, String cpf) {
    var validateExistsAgenda = exitsById(votingSession.getId())
        .flatMap(aBoolean -> {
          if (!aBoolean) {
            return Mono.error(new NotFoundException(
                "Not Found Voting Session with id: " + votingSession.getId()));
          }

          return Mono.just(true);
        });

    var validateUserAlreadyVote = repository.existsByIdAndVotesUserId(votingSession.getId(), vote.getUserId())
        .flatMap(aBoolean -> {
          if (aBoolean) {
            return Mono.error(new UserAlreadyVoteException(vote.getUserId()));
          }

          return Mono.just(true);
        });

    var validateVotingSessionIsOpen = isSessionOpen(votingSession.getId())
        .flatMap(aBoolean -> {
          if (!aBoolean) {
            return Mono.error(new ExpiredSessionTimeException(votingSession.getId()));
          }

          return Mono.just(true);
        });

    var validateUserCpfAbleToVote = userInfoService.getVoteStatusEnum(cpf)
        .flatMap(voteStatus -> {
          if (!voteStatus.getStatus().equals(VoteStatusEnum.ABLE_TO_VOTE)) {
            return Mono.error(new CpfNotValidException(cpf));
          }

          return Mono.just(true);
        });

    return Mono.when(validateExistsAgenda, validateUserAlreadyVote, validateVotingSessionIsOpen,
            validateUserCpfAbleToVote)
        .then();
  }

  private Mono<Void> validateVotingSessionCreate(VotingSession votingSession) {
    var validateExistsAgenda = agendaService.existsById(votingSession.getAgendaId().toString())
        .flatMap(aBoolean -> {
          if (!aBoolean) {
            return Mono.error(
                new NotFoundException("Not Found Agenda with id: " + votingSession.getAgendaId()));
          }

          return Mono.just(true);
        });

    return Mono.when(validateExistsAgenda).then();
  }
}
