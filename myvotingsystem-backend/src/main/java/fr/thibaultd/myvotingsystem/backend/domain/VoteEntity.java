package fr.thibaultd.myvotingsystem.backend.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "vote", indexes = {@Index(name = "ix_vote_election_id", columnList = "election_id")})
public class VoteEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Getter
  @NotNull
  private LocalDateTime votingTime;

  @Getter
  @NotNull
  @ManyToOne
  private ElectionEntity election;

  @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<VoteRankingEntity> ranking = new HashSet<>();


  protected VoteEntity() {}

  public VoteEntity(ElectionEntity election, List<CandidateEntity> ranking) {
    this.election = election;
    this.votingTime = LocalDateTime.now();

    int rank = 1;
    for (CandidateEntity candidate : ranking) {
      VoteRankingEntity voteRank = new VoteRankingEntity(rank++, this, candidate);
      this.ranking.add(voteRank);
    }
    this.election.incrementNbVotes();
  }

  public List<CandidateEntity> getRanking() {
    return ranking.stream()
        .sorted(Comparator.comparing(VoteRankingEntity::getRank))
        .map(VoteRankingEntity::getCandidate)
        .collect(Collectors.toList());
  }
}
