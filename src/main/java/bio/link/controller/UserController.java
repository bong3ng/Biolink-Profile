package bio.link.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.link.security.jwt.JwtTokenProvider;
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


}
