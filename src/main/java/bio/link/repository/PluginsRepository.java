package bio.link.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.PluginsEntity;
@Repository
public interface PluginsRepository extends JpaRepository<PluginsEntity , Long> {


    @Query(value = "SELECT * FROM plugins WHERE user_id = :userId",nativeQuery = true)
    List<PluginsEntity> getAllPluginsByUserId(@Param("userId") Long userId);
    @Query(value = "SELECT * FROM plugins WHERE user_id = :userId AND title = :pluginsTitle" , nativeQuery = true)
    PluginsEntity getPluginsByUserIdAndTitle(@Param("userId") Long userId, @Param("pluginsTitle") String pluginsTitle);


    //auth : khanhht13
    //date : 02/07/2022
    List<PluginsEntity> findByUserIdOrderByNumLocationAsc(Long userId);
}
