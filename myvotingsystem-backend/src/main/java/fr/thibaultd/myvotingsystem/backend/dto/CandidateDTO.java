package fr.thibaultd.myvotingsystem.backend.dto;

import fr.thibaultd.myvotingsystem.backend.domain.CandidateEntity;
import lombok.Getter;

public class CandidateDTO {

  @Getter
  private Integer id;

  @Getter
  private String name;


  protected CandidateDTO() {}

  public CandidateDTO(CandidateEntity candidateEntity) {
    this.id = candidateEntity.getId();
    this.name = candidateEntity.getName();
  }

}
