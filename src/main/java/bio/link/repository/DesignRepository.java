package bio.link.repository;


import bio.link.model.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Long> {

    DesignEntity findDesignEntityById(Long id);

    List<DesignEntity> findAllByUserId(Long userId);
}
