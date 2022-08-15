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

    @PutMapping("/{username}/social")
    public ResponseEntity clickSocial(@RequestHeader(required = false , value = "Authorization" ) String jwt,
                                      @RequestBody SocialEntity socialEntity) {
        if(jwt != null && jwtTokenProvider.getUserIdFromHeader(jwt) == socialEntity.getUserId()){
                return ResponseEntity.ok(new ResponseData(true,"CLICK thành công ", Arrays.asList("Bạn đang click vào social của mình")));
            }
        return ResponseEntity.ok(profileService.clickSocialOfProfile(socialEntity));
    }

    @PutMapping("/{username}/plugins")
    public ResponseEntity clickPlugins(@RequestHeader(required = false , value = "Authorization" ) String jwt,
                                       @RequestBody PluginsEntity pluginsEntity) {
        if(jwt != null && jwtTokenProvider.getUserIdFromHeader(jwt) == pluginsEntity.getUserId()) {
            return ResponseEntity.ok(new ResponseData(true,"CLICK thành công ", Arrays.asList("Bạn đang click vào plugin của mình")));
        }
        return ResponseEntity.ok(profileService.clickPluginsOfProfile(pluginsEntity));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity getProfile(@PathVariable String username,
                                     @RequestHeader(value = "Authorization" , required = false) String jwt) {
        Boolean checkGuest = true;
        if(jwt != null && jwtTokenProvider.getUserIdFromHeader(jwt) == userService.getUserByUsername(username).getId()) {
            checkGuest = false;
        }
        return ResponseEntity.ok(profileService.getUserProfileByUsername(username , checkGuest));
    }
    
    @GetMapping("/api/user/getProfile")
    public ResponseEntity getProfileByJWT(@RequestHeader("Authorization") String jwt) {
   
    	return ResponseEntity.ok(profileService.getUserProfileByJWT(jwt));
    }

    @GetMapping("/getAllProfile")
    public List<AllProfileDto> getAllProfile(@RequestHeader(required = false, value = "Authorization") String jwt) {

        return profileService.getAllProfile(jwt);
    }
    
   
}
