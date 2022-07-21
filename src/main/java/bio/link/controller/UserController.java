package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.link.service.UserServiceImpl;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/api/user/{username}/stats/{days}")
    public ResponseEntity getStats(@PathVariable String username,
                                   @PathVariable Integer days) {
        return ResponseEntity.ok(userService.getStatsByUsername(username , days));
    }

    @GetMapping("/link")
    public ResponseEntity getLinks() {
        return ResponseEntity.ok("Trang links");
    }
}
