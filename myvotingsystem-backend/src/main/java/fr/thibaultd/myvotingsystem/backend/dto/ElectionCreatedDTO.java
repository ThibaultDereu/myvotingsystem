package fr.thibaultd.myvotingsystem.backend.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import fr.thibaultd.myvotingsystem.backend.domain.CandidateEntity;
import fr.thibaultd.myvotingsystem.backend.domain.ElectionEntity;
import lombok.Getter;


public class ElectionCreatedDTO {

  @Getter
  private Integer id;

  @Getter
  @NotNull
  private String name;

  @Getter
  @NotNull
  private LocalDate closingDate;

  @Getter
  private List<String> candidateNames;

  protected ElectionCreatedDTO() {}

  public ElectionEntity toEntity() {
    ElectionEntity electionEntity = new ElectionEntity(this.name, this.closingDate);
    electionEntity.setCandidates(candidateNames);
    return electionEntity;
  }

  public ElectionCreatedDTO(ElectionEntity electionEntity) {
    this.id = electionEntity.getId();
    this.name = electionEntity.getName();
    this.closingDate = electionEntity.getClosingDate();
    this.candidateNames = electionEntity.getCandidates().stream().map(CandidateEntity::getName)
        .collect(Collectors.toList());
  }
}
