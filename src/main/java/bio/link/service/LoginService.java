package bio.link.service;

import bio.link.model.dto.LoginResponseDto;
import bio.link.model.entity.UserEntity;
import bio.link.security.oauth2.CustomOAuth2User;
import bio.link.security.payload.LoginRequest;
import bio.link.security.payload.Status;
import org.springframework.security.core.Authentication;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface LoginService {

    Status signUpUser(UserEntity user);

    Status sendVerificationForgotPassword(String email) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);

    Status updatePassword( String newPassword, String resetPasswordToken);

    LoginResponseDto loginFromSocial();

    Status createFirstLoginFromSocial(String username, String password, String jwt);

    LoginRequest checkStatusAccount(LoginRequest login);

    boolean checkFirstLogin(LoginRequest login);

    String checkLoginAccount(String username);

    void createAccountFromSocial(CustomOAuth2User oAuth2User, Authentication authentication);

}
