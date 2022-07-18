package com.sicredi.test.controller.votingsession.agenda;

import com.sicredi.test.model.Agenda;
import org.springframework.stereotype.Component;

@Component
public class AgendaMapper {

  public Agenda toEntity(AgendaRequest request) {
    return Agenda.builder()
        .name(request.getName())
        .description(request.getDescription())
        .build();
  }
}
