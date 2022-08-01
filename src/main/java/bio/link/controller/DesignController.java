package bio.link.controller;


import java.io.IOException;
import java.util.List;

import bio.link.security.payload.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.DesignEntity;
import bio.link.service.DesignService;
import bio.link.service.ProfileService;

@RestController
@RequestMapping("api/design")
@CrossOrigin("*")
public class DesignController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private DesignService designService;

    @GetMapping("/findByUserId")
    public List<DesignEntity> getAllByUserId(
            @RequestHeader("Authorization") String jwt
    ) {
        return designService.getAllByUserId(profileService.convertJwt(jwt));
    }

    @GetMapping("/findById")
    public DesignEntity getDesignById(
            @RequestHeader("Authorization") String jwt
    ) {
        return designService.getDesignById(profileService.convertJwt(jwt));
    }

    @PostMapping("/default")
    public DesignEntity createDefault(
            @ModelAttribute DesignEntity designEntity,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        return designService.createDefault(designEntity, image);
    }

    @PostMapping("")
    public DesignEntity create(
            @RequestHeader("Authorization") String jwt,
            @ModelAttribute DesignEntity designEntity,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        return designService.create(designEntity, image
                , profileService.convertJwt(jwt));
    }

//    @PutMapping("/{id}")
//    public DesignEntity update(
//            @RequestHeader("Authorization") String jwt,
//            @ModelAttribute DesignEntity designEntity,
//            @RequestParam(required = false) MultipartFile image,
//            @RequestParam("id") Long id
//    ) {
//        return designService.update(designEntity, image, id, profileService.convertJwt(jwt));
//    }

    @DeleteMapping("/deleteById")
    public Status delete(
            @RequestParam("id") Long id
    ) {
        return designService.delete(id);
    }
    
//    @PostMapping("/setting")
//    public Status updateShowAccount(@RequestParam("show_logo") Boolean showLogo,@RequestParam("show_nsfw") Boolean showNSFW, @RequestHeader("Authorization") String jwt) {
//    	return designService.updateShow(showLogo, showNSFW,profileService.convertJwt(jwt));
//    }
    

}
