package bio.link.repository;


import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import bio.link.model.dto.ClickProfileDto;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickProfileEntity;
@Repository
public interface ClickProfileRepository extends JpaRepository<ClickProfileEntity , Long> {

    ClickProfileEntity getClickCountByDateAndProfileId(@Param("date") LocalDate date , @Param("profileId") Long profileId);
    @Query(value = "SELECT * FROM click_profiles WHERE date = :date AND profile_id = :profileId LIMIT 1", nativeQuery = true)
    ClickProfileEntity getClickCountByDate(@Param("date") LocalDate date , @Param("profileId") Long profileId);


    @Query(value = "SELECT * FROM click_profiles WHERE profile_id = :profileId AND date >= :date " , nativeQuery = true)
    List<ClickProfileEntity> getAllClickBetween(@Param("profileId") Long profileId,
                                                @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE click_profiles SET click_count = :clickCount WHERE profile_id = :profileId AND date = :date" , nativeQuery = true)
    void updateProfileClickCount(@Param("date") LocalDate date ,
                                 @Param("profileId") Long profileId ,
                                 @Param("clickCount") Long clickCount);

    @Query(value = "SELECT users.username " +
                   "FROM users " +
                   "JOIN profiles " +
                   "ON profiles.user_id = users.id " +
                   "JOIN click_profiles " +
                   "ON click_profiles.profile_id = profiles.id " +
                   "WHERE profiles.id = :profileId LIMIT 1" , nativeQuery = true)
    String getUsernameByProfileId(@Param("profileId") Long profileId);
}
