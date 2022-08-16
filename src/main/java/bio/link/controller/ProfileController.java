package bio.link.controller;


import bio.link.model.dto.AllProfileDto;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.SocialEntity;

import bio.link.model.response.ResponseData;

import bio.link.service.UserService;

import bio.link.repository.LikesRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bio.link.security.jwt.JwtTokenProvider;

import bio.link.service.impl.ProfileServiceImpl;

import java.util.Arrays;


import bio.link.service.ProfileService;
import reactor.util.annotation.Nullable;

import java.util.List;


@RestController
@RequestMapping("")
@CrossOrigin("*")
public class ProfileController {
	@Autowired
	JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private UserService userService;

    @PutMapping("/api/{username}/social")
    public ResponseEntity clickSocial(@RequestHeader("Authorization") String jwt,
                                      @RequestBody SocialEntity socialEntity) {
        if( !jwt.equals("Bearer null") && jwtTokenProvider.getUserIdFromHeader(jwt) == socialEntity.getUserId()) {
            return ResponseEntity.ok("Bạn đang click vào social của mình");
        }
        return ResponseEntity.ok(profileService.clickSocialOfProfile(socialEntity));
    }

    @PutMapping("/api/{username}/plugins")
    public ResponseEntity clickPlugins(@RequestHeader("Authorization") String jwt,
                                       @RequestBody PluginsEntity pluginsEntity) {
        if( !jwt.equals("Bearer null") && jwtTokenProvider.getUserIdFromHeader(jwt) == pluginsEntity.getUserId()) {
            return ResponseEntity.ok("Bạn đang click vào plugin của mình");
        }
        return ResponseEntity.ok(profileService.clickPluginsOfProfile(pluginsEntity));
    }

    @GetMapping("/api/profile/{username}")
    public ResponseEntity getProfile(@PathVariable String username,
                                     @RequestHeader("Authorization") String jwt) {
        Boolean checkGuest = true;
        if( !jwt.equals("Bearer null") && jwtTokenProvider.getUserIdFromHeader(jwt) == userService.getUserByUsername(username).getId() ) {
            checkGuest = false;
        }
        return ResponseEntity.ok(profileService.getUserProfileByUsername(username , checkGuest));
    }
    
    @GetMapping("/api/user/getProfile")
    public ResponseEntity getProfileByJWT(@RequestHeader("Authorization") String jwt) {
    	return ResponseEntity.ok(profileService.getUserProfileByJWT(jwt));
    }

    @GetMapping("/api/getAllProfile")
    public List<AllProfileDto> getAllProfile(@RequestHeader(required = false, value = "Authorization") String jwt) {
        return profileService.getAllProfile(jwt);
    }
   
}
