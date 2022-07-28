package bio.link.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("")
    public List<DesignEntity> get() {
        return designService.getAll();
    }

    @GetMapping("/find/userId={userId}")
    public List<DesignEntity> getAllByUserId(
            @PathVariable("userId") Long userId
    ) {
        return designService.getAllByUserId(userId);
    }

    @GetMapping("/find/id={id}")
    public DesignEntity getDesign(
            @PathVariable("id") Long id
    ) {
        return designService.getDesignById(id);
    }
//    @PostMapping("/v1")
//    public DesignEntity getDesign(
//            @RequestParam String border,
//            @RequestParam String backGround,
//            @RequestParam String borderRadius,
//            @RequestParam String boxShadow,
//            @RequestParam String position,
//            @RequestParam String height,
//            @RequestParam String width,
//            @RequestParam String left,
//            @RequestParam String top,
//            @RequestParam String zIndex
//    ) {
//        return DesignEntity.builder()
//                .border(border)
//                .backGround(backGround)
//                .borderRadius(borderRadius)
//                .boxShadow(boxShadow)
//                .position(position)
//                .height(height)
//                .width(width)
//                .left(left)
//                .top(top)
//                .zIndex(zIndex)
//                .build();
//    }


    @PostMapping("")
    public DesignEntity create(
            @ModelAttribute DesignEntity designEntity,
            @RequestParam MultipartFile image,
            @RequestHeader("Authorization") String jwt
    ) {
        return designService.create(designEntity, image, profileService.convertJwt(jwt));
    }

    @PutMapping("/{id}")
    public DesignEntity update(
            @ModelAttribute DesignEntity designEntity,
            @RequestParam MultipartFile image,
            @RequestHeader("Authorization") String jwt,
            @PathVariable("id") Long id
    ) {
        return designService.update(designEntity, image, id, profileService.convertJwt(jwt));
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        designService.delete(id);
    }
    
//    @PostMapping("/setting")
//    public Status updateShowAccount(@RequestParam("show_logo") Boolean showLogo,@RequestParam("show_nsfw") Boolean showNSFW, @RequestHeader("Authorization") String jwt) {
//    	return designService.updateShow(showLogo, showNSFW,profileService.convertJwt(jwt));
//    }
    

}
