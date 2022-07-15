package bio.link.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ProfileEntity;
@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity , Long> {

    @Query(value = "SELECT * FROM profile WHERE user_id = :userId",nativeQuery = true)
    ProfileEntity getProfileByUserId(@Param("userId") Long userId) ;

}
