package bio.link.security.user;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bio.link.model.entity.UserEntity;
import bio.link.repository.UserRepository;
import bio.link.security.payload.Status;



@Service
public class CustomUserService implements UserDetailsService {
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
	
	public Status signUpUser(UserEntity user) {
		Status message = new Status();
		message.setSuccess(0);
		UserEntity userFindByUName = userRepo.findByUsername(user.getUsername());
		UserEntity userFindByEmail = userRepo.findByEmail(user.getEmail());
		
		
    	if (userFindByUName == null && userFindByEmail == null) {
    		LocalDate nowTime = LocalDate.now();
    		user.setCreatedAt(nowTime);
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		user.setRole("ROLE_USER");
    		userRepo.save(user);
    		message.setMessage("Tạo tài khoản thành công.");
    		message.setSuccess(1);
    	}else if(userFindByUName != null){
    		message.setMessage("Tên username đã tồn tại.");
    		
    	}else {
    		message.setMessage("Email đã tồn tại.");
    	}
		return message;
	}
	
	
}
