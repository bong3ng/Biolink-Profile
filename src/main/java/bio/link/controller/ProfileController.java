package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestHeader;
=======
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bio.link.service.ProfileServiceImpl;

@RestController
@RequestMapping("api/v1.0/profile")
public class ProfileController {

    @Autowired
    private ProfileServiceImpl profileService;

    @GetMapping("/{username}/")
    public ResponseEntity handle(@PathVariable String username ,
                               @RequestParam("title") String title ,
                               @RequestParam("url") String url ,
<<<<<<< HEAD
                               @RequestParam(name = "isPlugins" , required = false) Boolean isPlugins,
                               @RequestHeader("Authorization") String jwt) {
    		System.out.println(jwt);
=======
                               @RequestParam(name = "isPlugins" , required = false) Boolean isPlugins) {
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
        return ResponseEntity.ok(profileService.clickUrlOfUsername(username , title , url , isPlugins));
    }

    @GetMapping("/{username}")
    public ResponseEntity getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getUserProfileByUsername(username));
    }
}
