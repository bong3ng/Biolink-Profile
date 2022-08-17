package bio.link.controller;


import bio.link.model.dto.LikeDto;
import bio.link.model.entity.LikesEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.LikesRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.LikesService;
import bio.link.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/api/user")
@CrossOrigin("*")
public class LikesController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private LikesService likeService;

    @Autowired
    private LikesRepository likesRepository;


    @GetMapping("/getAllByUserId")
    public List<LikesEntity> getAllByUserId(
            @RequestHeader("Authorization") String jwt
    ) {
        return likeService.getAllByUserId(jwtTokenProvider.getUserIdFromHeader(jwt));
    }


    @GetMapping("/getLikeAndDislike")
    public LikeDto getAllLikeByProfileId(

            @RequestParam Long profileId
    ) {
        return likeService.getAllByProfileId(profileId);
    }

    @PostMapping("/saveLike")
    @ResponseStatus(HttpStatus.CREATED)
    public LikesEntity saveLike(
            @RequestParam(required = false, defaultValue = "false") String statusLike,
            @RequestParam String username,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        profileService.getAllProfile(jwt);
        return likeService.saveLike(statusLike , jwtTokenProvider.getUserIdFromHeader(jwt) , username);
    }

}
