package bio.link.service;


import bio.link.model.dto.CommentDto;
import bio.link.model.entity.CommentEntity;

import java.util.List;

public interface CommentService {

//
    List<CommentEntity> getAllCommentByProfileId(Long profileId);
//
    List<CommentDto> getCommentByProfileId(Long profileId);
//
    CommentEntity saveComment(
        String comment,
        Long userId,
        String username

    ) throws Exception;


}
