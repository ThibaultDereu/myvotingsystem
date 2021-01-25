package fr.thibaultd.myvotingsystem.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import fr.thibaultd.myvotingsystem.backend.domain.ElectionEntity;
import lombok.Getter;


public class ElectionDTO {

  @Getter
  private Integer id;

  @Getter
  @NotNull
  private String name;

  @Getter
  private int nbVotes;

  @Getter
  @NotNull
  private LocalDate closingDate;

  @Getter
  @NotNull
  private LocalDateTime creatingTime;

  @Getter
  private boolean open;


  public ElectionDTO(ElectionEntity election) {
    this.id = election.getId();
    this.name = election.getName();
    this.nbVotes = election.getNbVotes();
    this.closingDate = election.getClosingDate();
    this.creatingTime = election.getCreatingTime();
    this.open = election.isOpen();
  }
}
