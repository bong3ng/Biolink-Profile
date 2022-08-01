package bio.link.controller;


import bio.link.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bio.link.service.UserServiceImpl;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @GetMapping("/stats/{days}")
    public ResponseEntity getStats(@RequestHeader("Authorization") String jwt,
                                   @PathVariable Integer days) {
        Long userId = jwtTokenProvider.getUserIdFromHeader(jwt);
        return ResponseEntity.ok(userService.getStatsByUsername(userId , days));
    }

    @GetMapping("/link")
    public ResponseEntity getLinks() {
        return ResponseEntity.ok("Trang links");
    }
}
