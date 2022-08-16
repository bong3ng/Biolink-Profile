package bio.link.repository;

import bio.link.model.dto.LikeDto;
import bio.link.model.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {

    @Query(value = "SELECT * FROM likes WHERE profile_id = :profileId" , nativeQuery = true)
    List<LikeDto> getAllByProfileId(
            @Param("profileId") Long profileId
    );


    //cái này là để check xem user đang like hay dislike những ai
    @Query(value = "SELECT * FROM likes WHERE user_id = :userId" , nativeQuery = true)
    List<LikesEntity> getAllByUserId(
            @Param("userId") Long userId
    );


    //cái này là để check xem user có like profile này hay không
    @Query(value = "SELECT * FROM likes WHERE profile_id = :profileId AND user_id = :userId" , nativeQuery = true)
    LikesEntity status(
            @Param("profileId") Long profileId,
            @Param("userId") Long userId
    );


    //cái này là để đếm số like
    @Query(value = "SELECT COUNT(*) AS 'total_like' FROM likes WHERE profile_id = :profileId AND status_like = 1" , nativeQuery = true)
    Long countLikeByProfileId(
            @Param("profileId") Long profileId
    );



    //cái này là để đếm số dislike
//    @Query(value = "SELECT COUNT(*) AS 'total_dislike' FROM likes WHERE profile_id = :profileId AND status_like = 2" , nativeQuery = true)
//    Long countDislikeByProfileId(
//            @Param("profileId") Long profileId
//    );




}
