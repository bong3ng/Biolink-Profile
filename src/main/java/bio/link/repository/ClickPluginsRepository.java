package bio.link.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ClickPluginsEntity;

@Repository
public interface ClickPluginsRepository extends JpaRepository<ClickPluginsEntity, Long> {

<<<<<<< HEAD
    @Query(value = "SELECT * FROM click_plugins WHERE date = :date AND plugins_id = :pluginsId", nativeQuery = true)
=======
    @Query(value = "SELECT * FROM click_plugins WHERE date = :date AND plugins_id = :pluginsId LIMIT 1", nativeQuery = true)
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
    ClickPluginsEntity getClickCountByDate(@Param("date") LocalDate date , @Param("pluginsId") Long pluginsId );
}
