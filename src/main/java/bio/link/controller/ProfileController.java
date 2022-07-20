package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bio.link.model.entity.UserEntity;
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
                               @RequestParam(name = "isPlugins" , required = false) Boolean isPlugins
                              ) {

        return ResponseEntity.ok(profileService.clickUrlOfUsername(username , title , url , isPlugins));
    }

    @GetMapping("/api/profile/{username}")
    public ResponseEntity getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getUserProfileByUsername(username));
    }
    
    @GetMapping("/api/admin")
    public ResponseEntity getAllProfileAdmin() {
    	return ResponseEntity.ok(profileService.getUserByAdmin());
    }
    
    @PutMapping("/api/admin")
    public ResponseEntity updateUserByAdmin(@RequestBody UserEntity user) {
    	return ResponseEntity.ok(profileService.updateUserByAdmin(user));
    }
    
    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity deleteUserByAdmin(@PathVariable("id") Long id) {
    	return ResponseEntity.ok(profileService.deleteUserByAdmin(id));
    }
}
