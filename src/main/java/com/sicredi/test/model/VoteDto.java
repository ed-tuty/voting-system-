package com.sicredi.test.model;

import com.sicredi.test.service.votes.VoteEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VoteDto {
  private VoteEnum vote;
  private Integer userId;
}
