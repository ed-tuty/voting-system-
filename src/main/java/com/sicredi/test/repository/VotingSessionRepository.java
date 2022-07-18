package com.sicredi.test.repository;

import com.sicredi.test.model.VotingSession;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VotingSessionRepository extends ReactiveMongoRepository<VotingSession, String> {

  Flux<VotingSession> findAllByClosedAndEndDateLessThan(Boolean closed, LocalDateTime endDate);

  Flux<VotingSession> findAllByAgendaId(String agendaId);

  Mono<Boolean> existsByIdAndVotesUserId(String votingSessionId, Integer userId);
}
