package bio.link.service;


import bio.link.model.dto.CommentDto;
import bio.link.model.entity.CommentEntity;

import java.util.List;

public interface CommentService {


    List<CommentEntity> getAllRateByProfileId(Long profileId);

    List<CommentDto> getRateByProfileId(Long profileId);

    CommentEntity saveRate(
        String comment,
        Long userId,
        String username

    ) throws Exception;
}
