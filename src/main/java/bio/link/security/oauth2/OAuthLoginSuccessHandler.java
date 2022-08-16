package bio.link.security.oauth2;


import bio.link.security.user.CustomUserService;
import bio.link.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();


        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        loginService.createAccountFromSocial(oauth2User, authen);


//        response.sendRedirect("/success");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
