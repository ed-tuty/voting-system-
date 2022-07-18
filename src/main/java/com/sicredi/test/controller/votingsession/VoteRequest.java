package com.sicredi.test.controller.votingsession;

import com.sicredi.test.service.votes.VoteEnum;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoteRequest {

  @NotNull
  private VoteEnum vote;

  @NotNull
  private Integer userId;

  @NotNull
  private String cpf;
}
