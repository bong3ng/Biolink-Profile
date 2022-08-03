package bio.link.service;

import bio.link.model.entity.RateEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface RateService {

    List<RateEntity> getAllRateBuyProfileId(Long profileId);

    RateEntity createRate(
        String comment,
        Integer pointRate,
        Long userId,
        String username
    ) throws Exception;
}
