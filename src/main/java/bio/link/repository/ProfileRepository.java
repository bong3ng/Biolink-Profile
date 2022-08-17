package bio.link.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity , Long> {

   // @Query(value = "SELECT * FROM profiles WHERE user_id = :userId",nativeQuery = true)
    ProfileEntity getProfileByUserId(Long userId) ;

    String getImageByUserId(Long userId);


    ProfileEntity findOneById(Long id);
    
    ProfileEntity findByUserId(Long userId);

//    @Query(value = "SELECT id FROM profiles WHERE user_id = :userId" , nativeQuery = true)
//    Long getProfileIdByUserId (
//            @Param("userId") Long userId
//    );
}
