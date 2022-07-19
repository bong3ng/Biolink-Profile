package bio.link.repository;


import java.time.LocalDate;

import javax.transaction.Transactional;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickProfileEntity;
@Repository
public interface ClickProfileRepository extends JpaRepository<ClickProfileEntity , Long> {

    @Query(value = "SELECT * FROM click_profile WHERE date = :date AND profile_id = :profileId LIMIT 1", nativeQuery = true)
    ClickProfileEntity getClickCountByDate(@Param("date") LocalDate date , @Param("profileId") Long profileId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE click_profile SET click_count = :clickCount WHERE profile_id = :profileId AND date = :date" , nativeQuery = true)
    void updateProfileClickCount(@Param("date") LocalDate date , @Param("profileId") Long profileId , @Param("clickCount") Long clickCount);
}
