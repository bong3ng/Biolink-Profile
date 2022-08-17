package bio.link.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;


import bio.link.model.dto.LoginResponseDto;

import bio.link.service.LoginService;
import bio.link.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import bio.link.model.entity.UserEntity;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.payload.LoginRequest;
import bio.link.security.payload.LoginResponse;
import bio.link.security.payload.Status;
import bio.link.security.user.CustomUserDetails;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenProvider jwtProvider;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/login")
    public LoginResponseDto authenticateUser(@RequestBody LoginRequest loginRequest) throws IOException {
        loginRequest = loginService.checkStatusAccount(loginRequest);
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
                    .firstLogin(loginService.checkFirstLogin(loginRequest))
                    .username(loginRequest.getUsername()).build();
            success = true;
            message = "Đăng nhập thành công";
        } catch (Exception e) {
            message = loginService.checkLoginAccount(loginRequest.getUsername());
            login = null;
            success = false;
        }
        LoginResponseDto loginDto = LoginResponseDto.builder().loginResponse(login).message(message).success(success).build();
        return loginDto;

    }

    @PostMapping("/signup")
    public Status signUp(@RequestBody UserEntity user) throws UnsupportedEncodingException, MessagingException {
        return loginService.signUpUser(user);
    }



    @GetMapping("/verify")
    public Status verifyNewAccount(@RequestParam("code") String code) {
        boolean flag = loginService.verify(code);
        if (flag) {
            return new Status(true,"Xác thực email thành công.");
        }return new Status(false,"Sai liên kết");

    }

    @PostMapping("/forgotPassword")
    public Status forgotPass(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {


        return loginService.sendVerificationForgotPassword(email);
    }

    @PostMapping("/processForgot")
    public Status confirmPass(@RequestParam("token") String token, @RequestBody String password) {

        return loginService.updatePassword( password, token);
    }

    @PostMapping("/login/social")
    public LoginResponseDto loginFromSocial(){

        return loginService.loginFromSocial();
    }

    @PostMapping("/login/social/firstlogin")
    public Status createUserFromSocial(@RequestParam("username") String username, @RequestParam("password") String password, @RequestHeader("Authorization") String jwt){
        return loginService.createFirstLoginFromSocial(username, password, jwt);
    }

    @GetMapping("/user/checkUserRole")
    public Boolean handle(@RequestHeader("Authorization") String jwt) {
        return userService.checkUserRole(jwtProvider.getUserIdFromHeader(jwt));
    }
}