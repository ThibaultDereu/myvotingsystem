package fr.thibaultd.myvotingsystem.backend.dto;

import java.util.HashSet;
import java.util.Set;
import fr.thibaultd.myvotingsystem.backend.domain.CandidateEntity;
import fr.thibaultd.myvotingsystem.backend.domain.ElectionEntity;
import lombok.Getter;


public class ElectionWithCandidatesDTO extends ElectionDTO {

  @Getter
  private Set<CandidateDTO> candidates = new HashSet<>();

  public ElectionWithCandidatesDTO(ElectionEntity electionEntity) {
    super(electionEntity);
    for (CandidateEntity candidateEntity : electionEntity.getCandidates()) {
      candidates.add(new CandidateDTO(candidateEntity));
    }
  }
}
