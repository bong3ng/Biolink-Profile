package bio.link.repository;

import bio.link.model.entity.RateEntity;
import bio.link.model.response.ResponseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RateRepository extends JpaRepository<RateEntity ,  Long> {

    @Query(value = "SELECT * FROM rate WHERE profile_id = :profileId" , nativeQuery = true)
    List<RateEntity> getAllRateByProfileId(
            @Param("profileId") Long profileId
    );

    @Query(value = "SELECT * FROM rate WHERE profile_id = :profileId" , nativeQuery = true)
    List<RateEntity> getRateByProfileId(
            @Param("profileId") Long profileId
    );
}
