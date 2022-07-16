package bio.link.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickProfileEntity;
@Repository
public interface ClickProfileRepository extends JpaRepository<ClickProfileEntity , Long> {

<<<<<<< HEAD
    @Query(value = "SELECT * FROM click_profile WHERE date = :date AND profile_id = :profileId", nativeQuery = true)
=======
    @Query(value = "SELECT * FROM click_profile WHERE date = :date AND profile_id = :profileId LIMIT 1", nativeQuery = true)
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
    ClickProfileEntity getClickCountByDate(@Param("date") LocalDate date , @Param("profileId") Long profileId);

}
