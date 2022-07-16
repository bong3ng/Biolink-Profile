package bio.link.security.user;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendSimpleMessage(
		      String to, String subject, String text) {
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setFrom("vythanhlam100@gmail.com");
		        message.setTo(to); 
		        message.setSubject(subject); 
		        message.setText(text);
		        emailSender.send(message);
		    }

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
    		
    		sendSimpleMessage(user.getEmail(),"Đăng kí thành công","Chào mừng bạn đến với trang web của chúng tôi, chúc bạn vui vẻ hạnh phúc :)).");

    	}else if(userFindByUName != null){
    		message.setMessage("Tên username đã tồn tại.");
    		
    	}else {
    		message.setMessage("Email đã tồn tại.");
    	}
		return message;
	}
	
	public void updateResetPasswordToken(String token, String email) {
        UserEntity user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            System.out.println("Email chua dang ki");
        }
    }
     
    public UserEntity getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }
     
    public void updatePassword(UserEntity customer, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);
         
        customer.setResetPasswordToken(null);
        userRepo.save(customer);
    }
	
	
}
