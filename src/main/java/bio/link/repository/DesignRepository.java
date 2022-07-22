package bio.link.repository;


import bio.link.model.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Long> {

    DesignEntity findOneById(Long id);
}
