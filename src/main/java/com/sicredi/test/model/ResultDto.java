package com.sicredi.test.model;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultDto {

  private String id;
  private String name;
  private String description;

  private List<VotingSessionDto> votingSessions;

}
