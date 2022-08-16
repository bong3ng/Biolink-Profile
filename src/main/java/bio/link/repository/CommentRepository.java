package bio.link.repository;

import bio.link.model.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,  Long> {

    @Query(value = "SELECT * FROM comment WHERE profile_id = :profileId" , nativeQuery = true)
    List<CommentEntity> getAllRCommentByProfileId(
            @Param("profileId") Long profileId
    );




}
