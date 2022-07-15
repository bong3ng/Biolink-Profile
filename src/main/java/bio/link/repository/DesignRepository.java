package bio.link.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.DesignEntity;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Long> {

    DesignEntity findOneById(Long id);
}
