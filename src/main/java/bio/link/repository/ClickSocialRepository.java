package bio.link.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickSocialEntity;

@Repository
public interface ClickSocialRepository extends JpaRepository<ClickSocialEntity , Long> {

    @Query(value = "SELECT * FROM click_social WHERE date = :date AND social_id = :socialId LIMIT 1", nativeQuery = true)
    ClickSocialEntity getClickCountByDate( @Param("date") LocalDate date , @Param("socialId") Long socialId );
}
