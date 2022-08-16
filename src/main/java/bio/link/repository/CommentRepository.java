package bio.link.repository;

import bio.link.model.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,  Long> {

    @Query(value = "SELECT * FROM rate WHERE profile_id = :profileId" , nativeQuery = true)
    List<CommentEntity> getAllRateByProfileId(
            @Param("profileId") Long profileId
    );


    @Query(value = "SELECT * FROM rate WHERE profile_id = :profileId" , nativeQuery = true)
    List<CommentEntity> getRateByProfileId(
            @Param("profileId") Long profileId
    );

}
