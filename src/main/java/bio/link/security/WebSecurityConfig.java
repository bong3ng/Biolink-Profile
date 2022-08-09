package bio.link.security;


import bio.link.security.oauth2.CustomOAuth2UserService;
import bio.link.security.oauth2.OAuthLoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bio.link.security.jwt.JwtAuthenticationFilter;
import bio.link.security.user.CustomUserService;





@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	
	CustomUserService userService;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
            .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/api/user").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/admin").hasRole("ADMIN")// Cho phép tất cả mọi người truy cập vào 2 địa chỉ này

                    .anyRequest().permitAll()
                .and()
                .oauth2Login()

                    .userInfoEndpoint()
                        .userService(oauth2UserService)
                    .and()
                    .successHandler(oauthLoginSuccessHandler);



        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Autowired
    private CustomOAuth2UserService oauth2UserService;

    @Autowired
    private OAuthLoginSuccessHandler oauthLoginSuccessHandler;





}
