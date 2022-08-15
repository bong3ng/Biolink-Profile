package bio.link.service;



import bio.link.model.dto.CommentDto;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.CommentEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.CommentRepository;
import bio.link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository rateRepository;


    @Autowired
    private ProfileService profileService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<CommentEntity> getAllRateByProfileId(Long profileId) {

        return rateRepository.getAllRateByProfileId(profileId);
    }

    @Override

    public List<CommentDto> getRateByProfileId(Long profileId) {
        List<CommentEntity> rateEntityList = rateRepository.getAllRateByProfileId(profileId);
        List<CommentDto> rateDtoList = new ArrayList<>();

        for (int i = 0; i < rateEntityList.size(); i++) {
            String username = rateEntityList.get(i).getUsername();
            Long userId = rateEntityList.get(i).getUserId();
            ProfileEntity profileEntity = profileService.getProfileByUserId(userId);
            String image = profileEntity.getImage();

            CommentDto rateDto = CommentDto.builder().comment(rateEntityList.get(i).getComment())
                    .username(rateEntityList.get(i).getUsername())
                    .createDate(rateEntityList.get(i).getCreateDate())
                    .imageUser(image)
                    .build();
            rateDtoList.add(rateDto);
        }
        return rateDtoList;
    }

    @Override
    public CommentEntity saveRate(String comment,
                                  Long userId,
                                  String username) throws Exception {
        CommentEntity newRate = new CommentEntity();

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        newRate.setComment(comment);
        newRate.setProfileId(profileService.getUserByUsername(username).getId());
        newRate.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newRate.setUserId(userId);
        newRate.setUsername(userEntity.get().getUsername());

        return rateRepository.save(newRate);
    }
}
