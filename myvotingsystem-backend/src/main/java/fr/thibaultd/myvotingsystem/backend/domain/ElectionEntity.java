package fr.thibaultd.myvotingsystem.backend.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "election")
public class ElectionEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Getter
  @Setter
  @NotNull
  @NotBlank
  private String name;

  @Getter
  @Setter
  private int nbVotes;

  @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CandidateEntity> candidates;

  @Getter
  @NotNull
  private LocalDate closingDate;

  @Getter
  @NotNull
  private LocalDateTime creatingTime;

  protected ElectionEntity() {}

  public ElectionEntity(String name, LocalDate closingDate) {
    this.name = name;
    this.closingDate = closingDate;
    this.creatingTime = LocalDateTime.now();
  }

  public void setCandidates(Collection<String> candidateNames) {
    this.candidates = new HashSet<>();
    for (String candidateName : candidateNames) {
      this.candidates.add(new CandidateEntity(candidateName, this));
    }
  }

  public Set<CandidateEntity> getCandidates() {
    return Collections.unmodifiableSet(candidates);
  }

  public boolean isOpen() {
    return !this.closingDate.isBefore(LocalDate.now());
  }

  public void incrementNbVotes() {
    this.nbVotes++;
  }

}
