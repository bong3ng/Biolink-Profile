package bio.link.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bio.link.model.entity.SocialEntity;
import bio.link.service.SocialService;

@RestController
@CrossOrigin("*")
public class SocialController {

    @Autowired
    private SocialService socialService;


    @PostMapping("/social")
    @ResponseStatus(HttpStatus.CREATED)
    private SocialEntity saveSocial(
            @Valid
            @RequestParam String url,
            @RequestParam Long profile_id)  {
        return socialService.saveSocial(url , profile_id);
    }


    @GetMapping("/social")
    public List<SocialEntity> getAllSocial() {
        return socialService.getAllSocial();
    };


    @PutMapping ("/social/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SocialEntity updateSocial(
            @RequestParam String url ,
            @RequestParam Long profile_id,
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
