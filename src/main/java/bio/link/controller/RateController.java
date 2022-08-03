package bio.link.controller;


import bio.link.model.entity.RateEntity;
import bio.link.repository.RateRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RateController {

    @Autowired
    private RateService rateService;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/rate")
    public List<RateEntity> getAllRateByProfileId(
            @RequestHeader("Authorization") String jwt
    ) {
        return rateRepository.getAllRateByProfileId(jwtTokenProvider.getUserIdFromHeader(jwt));
    }
}
