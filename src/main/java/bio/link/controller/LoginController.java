package bio.link.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;


import bio.link.model.dto.LoginResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import bio.link.service.ProfileService;
import bio.link.service.SocialService;
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    SocialService socialService;
    @Autowired
    ProfileService profileService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserService userService;

    @Autowired
    JwtTokenProvider jwtProvider;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/login")

    public LoginResponseDto authenticateUser(@RequestBody LoginRequest loginRequest) throws IOException {
        loginRequest = userService.checkStatusAccount(loginRequest);
        String message;
        Boolean success;
        LoginResponse login = new LoginResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

            login = LoginResponse.builder()
                    .accessToken(jwt)
                    .firstLogin(userService.checkFirstLogin(loginRequest))
                    .username(loginRequest.getUsername()).build();
            success = true;
            message = "Đăng nhập thành công";
        } catch (Exception e) {
            message = userService.checkLoginAccount(loginRequest.getUsername());
            login = null;
            success = false;
        }
        LoginResponseDto loginDto = LoginResponseDto.builder().loginResponse(login).message(message).success(success).build();
        return loginDto;
    }

    @PostMapping("/signup")
    public Status signUp(@RequestBody UserEntity user) throws UnsupportedEncodingException, MessagingException {

        return userService.signUpUser(user);

    }



    @GetMapping("/verify")
    public Status verifyNewAccount(@RequestParam("code") String code) {
        boolean flag = userService.verify(code);
        if (flag) {
            return new Status(true,"Xác thực email thành công.");
        }return new Status(false,"Sai liên kết");

    }

    @PostMapping("/forgotPassword")
    public Status forgotPass(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {


        return userService.sendVerificationForgotPassword(email);
    }

    @PostMapping("/processForgot")
    public Status confirmPass(@RequestParam("token") String token, @RequestBody String password) {

        return userService.updatePassword( password, token);
    }

    @PostMapping("/login/social")
    public LoginResponseDto loginFromSocial(){

        return userService.loginFromSocial();
    }

}