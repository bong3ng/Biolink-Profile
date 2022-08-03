package bio.link.service;


import bio.link.model.entity.RateEntity;
import bio.link.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private ProfileService profileService;

    @Override
    public List<RateEntity> getAllRateBuyProfileId(Long profileId) {
        return rateRepository.getAllRateByProfileId(profileId);
    }

    @Override
    public RateEntity createRate( String comment,
                                  Integer pointRate,
                                  Long userId,
                                  String username ) throws Exception {
        RateEntity newRate = new RateEntity();
        newRate.setComment(comment);
        newRate.setPointRate(pointRate);
        newRate.setProfileId(profileService.getUserByUsername(username).getId());
        newRate.setCreateDate(LocalDateTime.now());
        newRate.setUserId(userId);

        return rateRepository.save(newRate);
    }
}
