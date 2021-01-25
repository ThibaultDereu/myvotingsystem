package fr.thibaultd.myvotingsystem.backend.repositories;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fr.thibaultd.myvotingsystem.backend.domain.VoteEntity;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {

  @Query("SELECT v FROM VoteEntity v "
      + "JOIN FETCH v.ranking r "
      + "WHERE v.election.id = :electionId ")
  public Set<VoteEntity> findByElectionId(@Param("electionId") Integer electionId);

}
