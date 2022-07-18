package bio.link.security.user;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	
	public void register(UserEntity user, String siteURL)
	        throws UnsupportedEncodingException, MessagingException {
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	     
	    String randomCode = RandomString.make(64);
	    user.setVerificationCode(randomCode);
	    user.setEnabled(false);
	     
	    userRepo.save(user);
	     
	    sendVerificationEmail(user, siteURL);
	}
     
	private void sendVerificationEmail(UserEntity user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String fromAddress = "vythanhlam100@gmail.com";
	    String senderName = "BioLink Profile";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "Your company name.";
	     
	    MimeMessage message = emailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getUsername());
	    String verifyURL = siteURL
	    		+ "/verify?code=" + user.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    emailSender.send(message);
	     
	}
	public boolean verify(String verificationCode) {
	    UserEntity user = userRepo.findByVerificationCode(verificationCode);
	     
	    if (user == null || user.isEnabled()) {
	        return false;
	    } else {
	        user.setVerificationCode(null);
	        user.setEnabled(true);
	        userRepo.save(user);
	         
	        return true;
	    }
	     
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
