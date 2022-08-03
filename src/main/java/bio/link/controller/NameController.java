package bio.link.controller;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import bio.link.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.ProfileEntity;
import bio.link.security.payload.Status;
import bio.link.service.ProfileService;

@RestController
@RequestMapping("api/user/profile")
@CrossOrigin("*")
public class NameController {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    @Autowired
    private ProfileService profileService;

    @Autowired
    private SocialService socialService;

    @GetMapping("/get")
    public List<ProfileEntity> getAllProfileUser() {
        return profileService.getAllProfileUser();
    }

    @GetMapping("")
    public ProfileEntity getProfile(
            @RequestHeader("Authorization") String jwt
    ) {
        return profileService.getProfileByUserId(profileService.convertJwt(jwt));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Status create(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String name,
            @RequestParam String bio
    ) throws IOException {

        return profileService.create(name, bio, profileService.convertJwt(jwt));
    }

    @PutMapping("")
    public ProfileEntity update(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String name,
            @RequestParam String bio,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        return profileService.update(name, bio, image, profileService.convertJwt(jwt));
    }

    @PutMapping("/active")
    public ProfileEntity updateDesign(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("designId") Long designId
    ) {
        return profileService.updateDesign(profileService.convertJwt(jwt), designId);
    }

    @PutMapping("/setting")
    public ProfileEntity updateSetting(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("showLogo")Boolean showLogo,
            @RequestParam("showNSFW") Boolean showNSFW
    ) {
        return profileService.updateSetting(profileService.convertJwt(jwt), showLogo, showNSFW);
    }
    
    

}
