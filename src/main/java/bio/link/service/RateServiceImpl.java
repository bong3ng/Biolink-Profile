package bio.link.service;


import bio.link.model.dto.RateDto;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.RateEntity;
import bio.link.model.entity.UserEntity;
import bio.link.model.response.ResponseData;
import bio.link.repository.RateRepository;
import bio.link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private ProfileService profileService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<RateEntity> getAllRateByProfileId(Long profileId) {
        return rateRepository.getAllRateByProfileId(profileId);
    }

    @Override
    public List<RateDto> getRateByProfileId(Long profileId) {
        List<RateEntity> rateEntityList = rateRepository.getAllRateByProfileId(profileId);
        List<RateDto> rateDtoList = new ArrayList<>();

        for (int i = 0; i < rateEntityList.size(); i++) {
            String username = rateEntityList.get(i).getUsername();
            Long userId = rateEntityList.get(i).getUserId();
            ProfileEntity profileEntity = profileService.getProfileByUserId(userId);
            String image = profileEntity.getImage();

            RateDto rateDto = RateDto.builder().comment(rateEntityList.get(i).getComment())
                    .pointRate(rateEntityList.get(i).getPointRate())
                    .username(rateEntityList.get(i).getUsername())
                    .createDate(rateEntityList.get(i).getCreateDate())
                    .imageUser(image)
                    .build();
            rateDtoList.add(rateDto);
        }
        return rateDtoList;
    }

    @Override
    public RateEntity saveRate( String comment,
                                  Integer pointRate,
                                  Long userId,
                                  String username) throws Exception {
        RateEntity newRate = new RateEntity();

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        newRate.setComment(comment);
        newRate.setPointRate(pointRate);
        newRate.setProfileId(profileService.getUserByUsername(username).getId());
        newRate.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newRate.setUserId(userId);
        newRate.setUsername(userEntity.get().getUsername());
        return rateRepository.save(newRate);
    }
}
