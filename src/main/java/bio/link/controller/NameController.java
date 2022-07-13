package bio.link.controller;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("")
    public List<ProfileEntity> get() {
        return profileService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileEntity create(
            @RequestParam String name,
            @RequestParam String bio,
            @RequestParam MultipartFile image
            ) throws IOException {
        return profileService.save(name, bio, image);
    }

    @PutMapping("")
    public ProfileEntity update(
            @RequestParam String name,
            @RequestParam String bio,
            @RequestParam MultipartFile image
    ) throws IOException {
        return profileService.save(name, bio, image);
    }
}
