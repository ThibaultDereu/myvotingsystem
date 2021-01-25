package fr.thibaultd.myvotingsystem.backend.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import fr.thibaultd.myvotingsystem.backend.domain.ElectionEntity;
import fr.thibaultd.myvotingsystem.backend.dto.ElectionCreatedDTO;
import fr.thibaultd.myvotingsystem.backend.dto.ElectionDTO;
import fr.thibaultd.myvotingsystem.backend.dto.ElectionWithCandidatesDTO;
import fr.thibaultd.myvotingsystem.backend.repositories.ElectionRepository;

@Component
@Path("/elections")
public class ElectionResource {

  @Autowired
  ElectionRepository electionRepository;

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public List<ElectionDTO> getAllElections() {
    List<ElectionEntity> elections =
        electionRepository.findAllByOrderByClosingDateDescCreatingTimeDesc();
    List<ElectionDTO> electionDtos =
        elections.stream().map(ElectionDTO::new).collect(Collectors.toList());
    return electionDtos;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public ElectionCreatedDTO createElection(ElectionCreatedDTO electionDTO) {
    ElectionEntity election = electionDTO.toEntity();
    electionRepository.save(election);
    return new ElectionCreatedDTO(election);
  }

  @DELETE
  public void deleteElection(Integer electionId) {
    electionRepository.deleteById(electionId);
  }

  @GET
  @Path("/{id}")
  @Produces({MediaType.APPLICATION_JSON})
  @Transactional
  public ElectionWithCandidatesDTO getElectionWithCandidates(@PathParam("id") Integer id) {
    ElectionEntity electionEntity = electionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This is entity does not exist."));
    ElectionWithCandidatesDTO electionDTO = new ElectionWithCandidatesDTO(electionEntity);
    return electionDTO;
  }
}
