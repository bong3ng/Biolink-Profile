package bio.link.security.user;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;


import javax.transaction.Transactional;



import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.oauth2.CustomOAuth2User;

import lombok.Data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.ProfileRepository;
import bio.link.repository.UserRepository;



@Data


@Service
public class CustomUserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	private JavaMailSender emailSender;


	@Autowired
	JwtTokenProvider tokenProvider;

	private Object authen;
	private Long idTemp;


	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = userRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		UserEntity user = userRepo.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return new CustomUserDetails(user);
	}


}
