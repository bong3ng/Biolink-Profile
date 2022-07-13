package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.link.service.UserServiceImpl;

@RestController
@RequestMapping("api/v1.0/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/stats")
    public ResponseEntity getStats() {
        return ResponseEntity.ok("Trang stats");
    }

    @GetMapping("/link")
    public ResponseEntity getLinks() {
        return ResponseEntity.ok("Trang links");
    }
}
