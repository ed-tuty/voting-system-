package com.sicredi.test.controller.votingsession;

import com.sicredi.test.model.Vote;
import com.sicredi.test.model.VotingSession;
import java.util.Collections;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class VotingSessionMapper {

  public VotingSession toEntity(VotingSessionRequest request) {
    return VotingSession.builder()
        .agendaId(new ObjectId(request.getAgendaId()))
        .closed(false)
        .votes(Collections.emptyList())
        .build();
  }

  public Vote toEntity(VoteRequest request) {
    return Vote.builder()
        .vote(request.getVote())
        .userId(request.getUserId())
        .build();
  }
}
