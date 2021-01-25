package fr.thibaultd.myvotingsystem.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;

@Entity
@Table(name = "vote_rank",
    uniqueConstraints = @UniqueConstraint(columnNames = {"vote_id", "candidate_id"}))
public class VoteRankingEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Getter
  private Integer rank;

  @Getter
  @ManyToOne
  private VoteEntity vote;

  @Getter
  @ManyToOne
  private CandidateEntity candidate;


  protected VoteRankingEntity() {}

  public VoteRankingEntity(Integer rank, VoteEntity vote, CandidateEntity candidate) {
    this.rank = rank;
    this.vote = vote;
    this.candidate = candidate;
  }

}
