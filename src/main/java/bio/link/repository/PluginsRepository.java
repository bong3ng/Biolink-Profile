package bio.link.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.PluginsEntity;
@Repository
public interface PluginsRepository extends JpaRepository<PluginsEntity , Long> {


    @Query(value = "SELECT * FROM plugins WHERE profile_id = :profileId",nativeQuery = true)
    List<PluginsEntity> getPluginsByProfileId(@Param("profileId") Long profileId);
    @Query(value = "SELECT * FROM plugins WHERE profile_id = :profileId AND title = :pluginsTitle" , nativeQuery = true)
    PluginsEntity getPluginsByProfileIdAndTitle(@Param("profileId") Long profileId, @Param("pluginsTitle") String pluginsTitle);
}
