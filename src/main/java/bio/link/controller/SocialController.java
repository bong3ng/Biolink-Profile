package bio.link.controller;



import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import bio.link.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import bio.link.model.entity.SocialEntity;
import bio.link.service.SocialService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")

public class SocialController {

    @Autowired
    private SocialService socialService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @GetMapping("/social")

    public List<SocialEntity> getAllSocialByUserId( @RequestHeader("Authorization") String jwt
    ) {


        return socialService.getAllSocialsByUserId(jwtTokenProvider.getUserIdFromHeader(jwt));
    };


    @PutMapping ("/updateSocial")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateSocial(
            @RequestBody List<SocialEntity> socialEntityList,
            @RequestHeader("Authorization") String jwt
    ) {
         socialService.updateSocial(socialEntityList , jwtTokenProvider.getUserIdFromHeader(jwt));
    }

}
