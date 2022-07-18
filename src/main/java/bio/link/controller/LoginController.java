package bio.link.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bio.link.model.entity.UserEntity;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.payload.LoginRequest;
import bio.link.security.payload.LoginResponse;
import bio.link.security.payload.Status;
import bio.link.security.user.CustomUserDetails;
import bio.link.security.user.CustomUserService;





@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    

    @Autowired
    CustomUserService userService;

    
    @Autowired
    JwtTokenProvider jwtProvider;


    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login/form")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    
    @PostMapping("/login/signup")
    public Status signUp(@RequestBody UserEntity user) throws UnsupportedEncodingException, MessagingException {
    	userService.register(user, "http://localhost:8080/");
    	return new Status(1,"ok");
    }
    
    @GetMapping("/test")
    public Status testabc(@RequestParam("token") String token) {
    	boolean flag = userService.verify(token);
    	
    	if(flag) {
    		return new Status(1,"Ok");
    	}else {
    		return new Status(0,"Sai token");
    	}
    }
}