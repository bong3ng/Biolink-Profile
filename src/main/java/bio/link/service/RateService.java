package bio.link.service;

import bio.link.model.entity.RateEntity;

import java.util.List;

public interface RateService {

    List<RateEntity> getAllRateBuyProfileId(Long profileId);

    RateEntity createRate(
        String comment,
        Integer pointRate,
        Long profileId,
        Long userId
    ) throws Exception;
}
