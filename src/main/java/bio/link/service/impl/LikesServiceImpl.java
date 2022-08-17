package bio.link.service.impl;


import bio.link.model.dto.LikeDto;
import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.LikesEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.LikesRepository;
import bio.link.repository.ProfileRepository;
import bio.link.repository.UserRepository;
import bio.link.service.LikesService;
import bio.link.service.ProfileService;
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

    @Autowired
    private ProfileRepository profileRepository;




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
            String statusLike,
            Long userId,
            String username
    ) throws Exception {

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        UserEntity usernameLikes = userRepository.findByUsername(username);
        ProfileEntity profileEntity = profileRepository.findByUserId(usernameLikes.getId());

        LikesEntity likesEntity = likesRepository.status(profileEntity.getId(),userId);

        if (likesEntity == null){
            LikesEntity likeEntity = new LikesEntity();
            likeEntity.setProfileId(profileEntity.getId());
            likeEntity.setUserId(userId);
            likeEntity.setUsernameLike(userEntity.get().getUsername());
            likeEntity.setStatusLike("true");
            return likesRepository.save(likeEntity);
        }else {
            if (likesEntity.getStatusLike() .equals("") || likesEntity.getStatusLike().equals("true")){
                likesEntity.setStatusLike("false");
            }else{
                likesEntity.setStatusLike("true");
            }

            return likesRepository.save(likesEntity);
        }
    }

}
