package bio.link.controller;



import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import bio.link.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import bio.link.model.entity.SocialEntity;
import bio.link.service.SocialService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")

public class SocialController {

    @Autowired
    private SocialService socialService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/createSocial")
    @ResponseStatus(HttpStatus.CREATED)
    private SocialEntity createSocial(
            @RequestParam String url,
            @RequestHeader("Authorization") String jwt ) throws IOException {
        return socialService.createSocial(url , jwtTokenProvider.getUserIdFromHeader(jwt));
    }


    @GetMapping("/social")
    public List<SocialEntity> getAllSocialByUserId(long userId) {

        return socialService.getAllSocialsByUserId(userId);
    };


    @PutMapping ("/social/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SocialEntity updateSocial(
            @RequestParam String url ,
            @PathVariable("id") Long id) {
        return socialService.updateSocial(url , id);
    }


    @RequestMapping(value= "/social/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteSocial(
            @PathVariable("id") Long id
    ) {
        socialService.deleteSocialById(id);
    }
}
