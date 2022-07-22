package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bio.link.service.UserServiceImpl;

@RestController
@RequestMapping("api/v1.0/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{username}/stats/{days}")
    public ResponseEntity getStats(@PathVariable String username,
                                   @PathVariable Integer days) {
        return ResponseEntity.ok(userService.getStatsByUsername(username , days));
    }

    @GetMapping("/link")
    public ResponseEntity getLinks() {
        return ResponseEntity.ok("Trang links");
    }
}
