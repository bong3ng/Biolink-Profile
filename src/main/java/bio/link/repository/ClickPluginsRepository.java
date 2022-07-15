package bio.link.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickPluginsEntity;

@Repository
public interface ClickPluginsRepository extends JpaRepository<ClickPluginsEntity, Long> {

    @Query(value = "SELECT * FROM click_plugins WHERE date = :date AND plugins_id = :pluginsId LIMIT 1", nativeQuery = true)
    ClickPluginsEntity getClickCountByDate(@Param("date") LocalDate date , @Param("pluginsId") Long pluginsId );
}
