package com.sicredi.test.kafka.message;

import com.sicredi.test.model.Agenda;
import com.sicredi.test.model.Vote;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultEvent {

  private Agenda agenda;
  private List<Vote> votes;
}
