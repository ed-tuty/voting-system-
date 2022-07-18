package com.sicredi.test.controller.votingsession;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionRequest {

  @NotNull
  private String agendaId;
  private Long sessionTime;
}
