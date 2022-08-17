package bio.link.service;

import bio.link.model.dto.LikeDto;
import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.LikesEntity;
import bio.link.model.entity.ProfileEntity;

import java.util.List;

public interface LikesService {


   List<LikesEntity> getAllByUserId(Long userId);


    LikeDto getAllByProfileId(Long profileId);


    LikesEntity saveLike(
            String statusLike,
            Long userId,
            String usernameCmt
    ) throws Exception;
}
