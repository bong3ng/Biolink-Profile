package bio.link.controller;


import java.util.List;

import bio.link.model.entity.DesignEntity;
import bio.link.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import bio.link.service.DesignService;

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
            @RequestHeader("Authorization") String jwt,
            @RequestBody DesignEntity designEntity
    ) {
        return designService.create(profileService.convertJwt(jwt), designEntity);
    }

    @PutMapping("/{id}")
    public DesignEntity update(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt,
            @RequestBody DesignEntity designEntity
    ) {
        return designService.update(id, profileService.convertJwt(jwt), designEntity);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        designService.delete(id);
    }
}
