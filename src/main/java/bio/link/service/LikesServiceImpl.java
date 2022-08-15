package bio.link.service;


import bio.link.model.dto.LikeDto;
import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.LikesEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.LikesRepository;
import bio.link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;




    @Override
    public List<LikesEntity> getAllByUserId(Long userId) {
        return likesRepository.getAllByUserId(userId);
    }


    @Override
    public LikeDto getAllByProfileId(Long profileId) {

//        LikeDto likeDto = new LikeDto();
//        likeDto.setTotalLike(likesRepository.countLikeByProfileId(profileId));
//        likeDto.setTotalDislike(likesRepository.countDislikeByProfileId(profileId));

        return null;
    }

    @Override
    public LikesEntity saveLike(
            Boolean statusLike,
            Long userId,
            String usernameCmt
            ) throws Exception {

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        LikesEntity likesEntity = likesRepository.status(profileService.getUserByUsername(usernameCmt).getId(),userId);

        if (likesEntity == null){
            LikesEntity likeEntity = new LikesEntity();
            likeEntity.setProfileId(profileService.getUserByUsername(usernameCmt).getId());
            likeEntity.setUserId(userId);
            likeEntity.setUsernameCmt(userEntity.get().getUsername());
            likeEntity.setStatusLike(statusLike);
            return likesRepository.save(likeEntity);
        }else {
            likesEntity.setStatusLike(false);
            return likesRepository.save(likesEntity);
        }
    }
}
