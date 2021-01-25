package fr.thibaultd.myvotingsystem.backend.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import fr.thibaultd.myvotingsystem.backend.domain.CandidateEntity;
import fr.thibaultd.myvotingsystem.backend.domain.VoteResults;
import lombok.Getter;

public class VoteResultsDTO {

  @Getter
  private String electionName;
  @Getter
  private Integer nbVotes;
  @Getter
  private List<CandidateDTO> candidates = new ArrayList<>();
  @Getter
  private List<CandidateDTO> winners = new ArrayList<>();
  @Getter
  private Map<Integer, Map<Integer, Integer>> adjacencyMap = new HashMap<>();


  public VoteResultsDTO(VoteResults voteResults) {

    this.electionName = voteResults.getElection().getName();

    this.nbVotes = voteResults.getElection().getNbVotes();

    Map<CandidateEntity, Map<CandidateEntity, Integer>> entityAdjacencyMap =
        voteResults.getAdjacencyMap();

    this.candidates = entityAdjacencyMap.keySet().stream()
        .map(cand -> new CandidateDTO(cand))
        .collect(Collectors.toList());

    this.winners = voteResults.getWinners().stream()
        .map(cand -> new CandidateDTO(cand))
        .collect(Collectors.toList());

    // build adjacency map
    for (CandidateEntity cand1 : entityAdjacencyMap.keySet()) {

      Map<CandidateEntity, Integer> entityMapOpponents = entityAdjacencyMap.get(cand1);
      Map<Integer, Integer> mapOpponents = new HashMap<>();

      for (CandidateEntity cand2 : entityMapOpponents.keySet()) {
        mapOpponents.put(cand2.getId(), entityMapOpponents.get(cand2));
      }
      this.adjacencyMap.put(cand1.getId(), mapOpponents);
    }

  }
}
