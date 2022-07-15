package bio.link.user;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bio.link.payload.RandomStuff;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
	
	public RandomStuff signUpUser(UserEntity user) {
		RandomStuff message = new RandomStuff(null);
		UserEntity userFind = userRepo.findByUsernameAndEmail(user.getUsername(), user.getEmail());
    	if (userFind == null) {
    		LocalDate nowTime = LocalDate.now();
    		user.setCreatedAt(nowTime);
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		user.setRole("ROLE_USER");
    		userRepo.save(user);
    		message.setMessage("Tao tai khoan thanh cong");
    	}else {
    		message.setMessage("Tao tai khoan that bai");
    	}
		return message;
	}
}
