package com.sicredi.test.controller.votingsession.agenda;

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
public class AgendaRequest {

  @NotNull
  private String name;

  @NotNull
  private String description;
}
