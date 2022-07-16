package bio.link.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.SocialEntity;

@Repository
public interface SocialRepository extends JpaRepository<SocialEntity, Long> {
    @Query(value = "SELECT * FROM social WHERE profile_id = :profileId",nativeQuery = true)
    List<SocialEntity> getAllSocialsByProfileId(@Param("profileId") Long profileId);

    @Query(value = "SELECT * FROM social WHERE profile_id = :profileId AND name = :socialName LIMIT 1", nativeQuery = true)
    SocialEntity getSocialByProfileIdAndName(@Param("profileId") Long profileId , @Param("socialName") String socialName);
}
