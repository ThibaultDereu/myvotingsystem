package fr.thibaultd.myvotingsystem.backend.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public class VoteResults {

  @Getter
  private final ElectionEntity election;
  private final Set<VoteEntity> votes;
  @Getter
  private Map<CandidateEntity, Map<CandidateEntity, Integer>> adjacencyMap = new HashMap<>();
  @Getter
  private Set<CandidateEntity> winners = new HashSet<>();


  @EqualsAndHashCode(of = {"candidate"})
  private static class CandidateNode {

    private final CandidateEntity candidate;
    // map containing the opponents who beat this candidate, and the margin
    private Map<CandidateNode, Integer> nodesLose = new HashMap<>();

    private CandidateNode(CandidateEntity candidate) {
      this.candidate = candidate;
    }

    private void loseAgainst(CandidateNode otherNode, int margin) {
      this.nodesLose.put(otherNode, margin);
    }
  }



  public VoteResults(ElectionEntity election, Collection<VoteEntity> votes) {
    this.election = election;
    this.votes = new HashSet<>(votes);
    this.adjacencyMap = this.calculateAdjacencyMap();
    this.winners = this.calculateSchulzeWinners();
  }


  private Map<CandidateEntity, Map<CandidateEntity, Integer>> calculateAdjacencyMap() {

    Map<CandidateEntity, Map<CandidateEntity, Integer>> adjacencyMap = new HashMap<>();

    // register all the scores in the adjacency map
    for (VoteEntity vote : votes) {
      List<CandidateEntity> ranking = vote.getRanking();

      for (int i = 0; i < ranking.size(); i++) {
        // the winning candidate (in a given vote) is before all its losing opponents
        CandidateEntity candidateWin = ranking.get(i);
        Map<CandidateEntity, Integer> mapOpponents =
            adjacencyMap.computeIfAbsent(candidateWin, cand -> new HashMap<>());

        for (int j = i + 1; j < ranking.size(); j++) {
          CandidateEntity candidateLose = ranking.get(j);
          mapOpponents.merge(candidateLose, 1, (a, b) -> a + b);
        }
      }
    }

    return adjacencyMap;
  }


  private Set<CandidateNode> calculateCandidateGraph() {

    // build a graph of CandidateNodes from the adjacency map
    Map<CandidateEntity, CandidateNode> mapCandidateNodes = this.adjacencyMap.keySet().stream()
        .collect(Collectors.toMap(cand -> cand, cand -> new CandidateNode(cand)));

    List<CandidateEntity> listCandidates = new ArrayList<>(this.adjacencyMap.keySet());

    for (int i = 0; i < listCandidates.size(); i++) {
      CandidateEntity cand1 = listCandidates.get(i);
      CandidateNode node1 = mapCandidateNodes.get(cand1);

      for (int j = i + 1; j < listCandidates.size(); j++) {
        CandidateEntity cand2 = listCandidates.get(j);
        CandidateNode node2 = mapCandidateNodes.get(cand2);

        int score1vs2 = this.adjacencyMap.get(cand1).getOrDefault(cand2, 0);
        int score2vs1 = this.adjacencyMap.get(cand2).getOrDefault(cand1, 0);
        int margin = Math.abs(score1vs2 - score2vs1);

        if (score1vs2 < score2vs1) {
          node1.loseAgainst(node2, margin);
        } else if (score2vs1 < score1vs2) {
          node2.loseAgainst(node1, margin);
        }
      }
    }

    Set<CandidateNode> candidateGraph = new HashSet<>(mapCandidateNodes.values());
    return candidateGraph;
  }


  /**
   * <ol>
   * <li>Calculate the Schwartz set based only on undropped defeats.</li>
   * <li>If there are no defeats among the members of that set then they (plural in the case of a
   * tie) win and the count ends.</li>
   * <li>Otherwise, drop the weakest defeat among the candidates of that set. Go to 1.</li>
   * </ol>
   * ref: https://electowiki.org/wiki/Schulze_method.<br/>
   * 
   * @param candidateGraph
   * @return
   */
  private Set<CandidateEntity> calculateSchulzeWinners() {

    // build a graph where all losing candidates points to their winning opponents
    Set<CandidateNode> candidateGraph = this.calculateCandidateGraph();

    boolean defeatsExist = true;

    while (defeatsExist) {
      // calculate the schwartz set
      candidateGraph = this.getSchwartzSet(candidateGraph);

      // are there still any defeats ?
      defeatsExist = candidateGraph.stream().anyMatch(cand -> !cand.nodesLose.isEmpty());

      // drop the weakest defeat(s)
      if (defeatsExist) {
        // search
        final int weakestDefeat = candidateGraph.stream()
            .flatMap(cand -> cand.nodesLose.values().stream())
            .min(Integer::compare).orElse(Integer.MAX_VALUE);

        // drop
        for (CandidateNode cand : candidateGraph) {
          cand.nodesLose.values().removeIf(nbLosses -> nbLosses == weakestDefeat);
        }
      }
    }

    Set<CandidateEntity> schulzeWinners = candidateGraph.stream()
        .map(cand -> cand.candidate)
        .collect(Collectors.toSet());
    return schulzeWinners;
  }


  /**
   * The definition of a Schwartz set, as used in the Schulze method, is as follows:
   *
   * <li>An unbeaten set is a set of candidates of whom none is beaten by anyone outside that
   * set.</li>
   * <li>An innermost unbeaten set is an unbeaten set that doesn't contain a smaller unbeaten
   * set.</li>
   * <li>The Schwartz set is the set of candidates who are in innermost unbeaten sets.</li> <br/>
   * ref: https://electowiki.org/wiki/Schulze_method
   * 
   * @param candidateGraph
   * @return
   */
  private Set<CandidateNode> getSchwartzSet(Set<CandidateNode> candidateGraph) {

    // find unbeaten sets
    Set<Set<CandidateNode>> unbeatenSets = new HashSet<>();

    for (CandidateNode node : candidateGraph) {
      unbeatenSets.add(this.getUnbeatenSetFrom(node));
    }

    // find innermost unbeaten sets
    Set<CandidateNode> schwartzSet = new HashSet<>();

    for (Set<CandidateNode> setToCheck : unbeatenSets) {
      boolean isMinimal = true;
      for (Set<CandidateNode> otherSet : unbeatenSets) {
        if (setToCheck != otherSet && setToCheck.containsAll(otherSet)) {
          isMinimal = false;
          break;
        }
      }

      if (isMinimal) {
        schwartzSet.addAll(setToCheck);
      }
    }

    return schwartzSet;
  }


  private Set<CandidateNode> getUnbeatenSetFrom(CandidateNode node) {
    Set<CandidateNode> visitedNodes = new HashSet<>();
    Queue<CandidateNode> toVisit = new ArrayDeque<>();
    toVisit.offer(node);

    while (!toVisit.isEmpty()) {
      CandidateNode currentNode = toVisit.poll();
      visitedNodes.add(currentNode);
      for (CandidateNode nodeLose : currentNode.nodesLose.keySet()) {
        if (!visitedNodes.contains(nodeLose)) {
          toVisit.offer(nodeLose);
        }
      }
    }

    return visitedNodes;
  }

}
