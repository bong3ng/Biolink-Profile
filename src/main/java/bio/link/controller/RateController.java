package bio.link.controller;


import bio.link.model.entity.RateEntity;
import bio.link.repository.RateRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping("/saveRate")
    @ResponseStatus(HttpStatus.CREATED)
    public RateEntity saveRate(
            @RequestParam(required = false) String comment,
            @RequestParam Integer pointRate,
            @RequestParam String username,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return rateService.createRate(comment , pointRate , jwtTokenProvider.getUserIdFromHeader(jwt),username);
    }

}
