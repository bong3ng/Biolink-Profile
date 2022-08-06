package bio.link.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.SocialEntity;

@Repository
public interface SocialRepository extends JpaRepository<SocialEntity, Long> {
    @Query(value = "SELECT * FROM socials WHERE user_id = :userId",nativeQuery = true)
    List<SocialEntity> getAllSocialsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM socials WHERE user_id = :userId AND name = :socialName LIMIT 1", nativeQuery = true)
    SocialEntity getSocialByUserIdAndName(@Param("userId") Long userId , @Param("socialName") String socialName);
}
