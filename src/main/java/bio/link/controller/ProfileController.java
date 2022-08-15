package bio.link.controller;


import bio.link.model.dto.AllProfileDto;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.SocialEntity;
import bio.link.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.ProfileServiceImpl;
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

    @PutMapping("/{username}/social")
    public ResponseEntity clickSocial(@RequestBody SocialEntity socialEntity) {
        return ResponseEntity.ok(profileService.clickSocialOfProfile(socialEntity));
    }

    @PutMapping("/{username}/plugins")
    public ResponseEntity clickPlugins(@RequestBody PluginsEntity pluginsEntity) {
        return ResponseEntity.ok(profileService.clickPluginsOfProfile(pluginsEntity));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getUserProfileByUsername(username));
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
