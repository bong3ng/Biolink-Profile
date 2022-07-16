package bio.link.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickSocialEntity;

@Repository
public interface ClickSocialRepository extends JpaRepository<ClickSocialEntity , Long> {

<<<<<<< HEAD
    @Query(value = "SELECT * FROM click_social WHERE date = :date AND social_id = :socialId", nativeQuery = true)
=======
    @Query(value = "SELECT * FROM click_social WHERE date = :date AND social_id = :socialId LIMIT 1", nativeQuery = true)
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
    ClickSocialEntity getClickCountByDate( @Param("date") LocalDate date , @Param("socialId") Long socialId );
}
