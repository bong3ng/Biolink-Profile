package bio.link.service;


import bio.link.model.dto.CommentDto;
import bio.link.model.entity.CommentEntity;

import java.util.List;

public interface CommentService {

//


//
    List<CommentDto> getAllCommentByProfileId(Long profileId);
//
    CommentEntity saveComment(
        String comment,
        Long userId,
        String username

    ) throws Exception;


}
