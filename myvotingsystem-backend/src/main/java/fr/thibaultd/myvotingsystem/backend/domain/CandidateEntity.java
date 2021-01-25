package fr.thibaultd.myvotingsystem.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "candidate",
    indexes = {@Index(name = "ix_candidate_election_id", columnList = "election_id")})
public class CandidateEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @NotBlank
  @Getter
  @Setter
  private String name;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? super.hashCode() : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CandidateEntity other = (CandidateEntity) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return super.equals(obj);
  }

  @Getter
  @ManyToOne
  private ElectionEntity election;


  protected CandidateEntity() {}

  public CandidateEntity(String name, ElectionEntity election) {
    this.name = name;
    this.election = election;
  }

}
