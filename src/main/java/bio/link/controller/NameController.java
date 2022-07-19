package bio.link.controller;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import bio.link.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.ProfileEntity;
import bio.link.service.ProfileService;

@RestController
@RequestMapping("api/profile")
@CrossOrigin("*")
public class NameController {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public ProfileEntity getProfile(
            @RequestHeader("Authorization") String jwt
    ) {
        return profileService.getProfileByUserId(profileService.convertJwt(jwt));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileEntity create(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String name,
            @RequestParam String bio,
            @RequestParam MultipartFile image
    ) throws IOException {
        return profileService.create(name, bio, image, profileService.convertJwt(jwt));
    }

    @PutMapping("")
    public ProfileEntity update(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String name,
            @RequestParam String bio,
            @RequestParam MultipartFile image
    ) throws IOException {
        return profileService.update(name, bio, image, profileService.convertJwt(jwt));
    }

    @PutMapping("/active")
    public ProfileEntity updateDesign(
            @RequestHeader("Authorization") String jwt,
            @RequestParam Long designId
    ) {
        return profileService.updateDesign(profileService.convertJwt(jwt), designId);
    }

    @PutMapping("/show-logo")
    public ProfileEntity updateSetting(
            @RequestHeader("Authorization") String jwt,
            @RequestParam Boolean showLogo,
            @RequestParam Boolean showNsfw
    ) {
        return profileService.updateSetting(profileService.convertJwt(jwt), showLogo, showNsfw);
    }

}
