package bio.link.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickSocialEntity;

import javax.transaction.Transactional;

@Repository
public interface ClickSocialRepository extends JpaRepository<ClickSocialEntity , Long> {


    @Query(value = "SELECT * FROM click_socials WHERE date = :date AND social_id = :socialId LIMIT 1", nativeQuery = true)
    ClickSocialEntity getClickCountByDate( @Param("date") LocalDate date , @Param("socialId") Long socialId );

    @Query(value = "SELECT SUM(click_count) FROM click_socials WHERE date >= :date AND social_id = :socialId", nativeQuery = true)
    Long getAllClickCountBetween(@Param("socialId") Long socialId , @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE click_socials SET click_count = :clickCount WHERE social_id = :socialId AND date = :date" , nativeQuery = true)
    void updateSocialClickCount(@Param("date") LocalDate date , @Param("socialId") Long socialId , @Param("clickCount") Long clickCount);
}
