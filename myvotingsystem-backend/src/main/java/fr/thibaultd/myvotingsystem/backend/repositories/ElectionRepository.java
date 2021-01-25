package fr.thibaultd.myvotingsystem.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.thibaultd.myvotingsystem.backend.domain.ElectionEntity;

@Repository
public interface ElectionRepository extends JpaRepository<ElectionEntity, Integer> {

  public List<ElectionEntity> findAllByOrderByClosingDateDescCreatingTimeDesc();
}
