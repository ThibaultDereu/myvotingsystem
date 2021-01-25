package fr.thibaultd.myvotingsystem.backend.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import fr.thibaultd.myvotingsystem.backend.domain.CandidateEntity;
import fr.thibaultd.myvotingsystem.backend.domain.ElectionEntity;
import fr.thibaultd.myvotingsystem.backend.domain.VoteEntity;
import fr.thibaultd.myvotingsystem.backend.domain.VoteResults;
import fr.thibaultd.myvotingsystem.backend.dto.CandidateDTO;
import fr.thibaultd.myvotingsystem.backend.dto.VoteResultsDTO;
import fr.thibaultd.myvotingsystem.backend.repositories.ElectionRepository;
import fr.thibaultd.myvotingsystem.backend.repositories.VoteRepository;

@Component
@Path("/votes")
public class VoteResource {

  @Autowired
  VoteRepository voteRepository;
  @Autowired
  ElectionRepository electionRepository;


  @Path("/{electionId}")
  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Transactional
  public void submitVote(@PathParam("electionId") Integer electionId,
      List<CandidateDTO> candidates) {

    ElectionEntity election = electionRepository.findById(electionId)
        .orElseThrow(() -> new IllegalArgumentException("The election entity does not exist."));

    // build a candidate entity list in the ordered like the candidateDto list
    Map<Integer, CandidateEntity> mapCandidatesByIds = election.getCandidates().stream()
        .collect(Collectors.toMap(CandidateEntity::getId, cand -> cand));
    List<CandidateEntity> candidateRanking = new ArrayList<>();

    for (CandidateDTO candidateDTO : candidates) {
      candidateRanking.add(mapCandidatesByIds.get(candidateDTO.getId()));
    }

    // create the vote and persist it
    VoteEntity vote = new VoteEntity(election, candidateRanking);
    voteRepository.save(vote);
  }


  @Path("/{electionId}/results")
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public VoteResultsDTO getResults(@PathParam("electionId") Integer electionId) {

    ElectionEntity election = this.electionRepository.findById(electionId)
        .orElseThrow(() -> new IllegalArgumentException("unknown election."));
    Set<VoteEntity> votes = this.voteRepository.findByElectionId(electionId);

    VoteResults voteResults = new VoteResults(election, votes);

    VoteResultsDTO voteResultsDTO = new VoteResultsDTO(voteResults);
    return voteResultsDTO;

  }


}
