package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bio.link.service.ProfileServiceImpl;

@RestController
@RequestMapping("")
public class ProfileController {

    @Autowired
    private ProfileServiceImpl profileService;

    @GetMapping("/api/{username}")
    public ResponseEntity handle(@PathVariable String username ,
                               @RequestParam("title") String title ,
                               @RequestParam("url") String url ,
                               @RequestParam(name = "isPlugins" , required = false) Boolean isPlugins,
                               @RequestHeader("Authorization") String jwt) {

        return ResponseEntity.ok(profileService.clickUrlOfUsername(username , title , url , isPlugins));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getUserProfileByUsername(username));
    }
}
