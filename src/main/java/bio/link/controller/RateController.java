package bio.link.controller;



import bio.link.model.dto.RateDto;

import bio.link.model.entity.RateEntity;
import bio.link.repository.RateRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.RateService;

import bio.link.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController

@RequestMapping("/api/user")
@CrossOrigin("*")

public class RateController {

    @Autowired
    private RateService rateService;

    @Autowired

    private UserService userService;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/rate")

    public List<RateDto> getAllRateByProfileId(
            @RequestHeader("Authorization") String jwt
    ) {
        return rateService.getRateByProfileId(jwtTokenProvider.getUserIdFromHeader(jwt));
    }

    @PostMapping("/saveRate")
    @ResponseStatus(HttpStatus.CREATED)
    public RateEntity saveRate(
            @RequestParam(required = false) String comment,
            @RequestParam Integer pointRate,
            @RequestParam String username,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        return rateService.saveRate(comment , pointRate ,  jwtTokenProvider.getUserIdFromHeader(jwt),username);
    }


}
