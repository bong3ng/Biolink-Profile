package bio.link.security.user;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.oauth2.CustomOAuth2User;
import bio.link.security.oauth2.Provide;
import bio.link.security.payload.LoginResponse;
import lombok.Data;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.ProfileRepository;
import bio.link.repository.UserRepository;
import bio.link.security.payload.LoginRequest;
import bio.link.security.payload.Status;
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

	// Đăng kí user
	public Status signUpUser(UserEntity user) {
		
		Status message = new Status();
		message.setSuccess(false);
		UserEntity userFindByUName = userRepo.findByUsername(user.getUsername());
		UserEntity userFindByEmail = userRepo.findByEmail(user.getEmail());

		if (userFindByUName != null) {
			message.setMessage("Tên username đã tồn tại.");
		} else if (userFindByEmail != null) {
			message.setMessage("Email đã tồn tại.");
		} else {
			LocalDate nowTime = LocalDate.now();
			user.setCreatedAt(nowTime);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			user.setStatus(true);
			

//    		sendSimpleMessage(user.getEmail(),"Đăng kí thành công","Chào mừng bạn đến với trang web của chúng tôi, chúc bạn vui vẻ hạnh phúc :)).");
			try {
				// Tạo verificationCode
				String randomCode = RandomString.make(64);
				user.setVerificationCode(randomCode);
				user.setEnabled(false);
				

				userRepo.save(user);
				sendVerificationEmail(user, "http://localhost:3000/");

				message.setMessage("Tạo tài khoản thành công.");
				message.setSuccess(true);
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (MessagingException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		return message;
	}


	public Status sendVerificationForgotPassword(String email) throws MessagingException, UnsupportedEncodingException {
		String token = RandomString.make(30);
		

		UserEntity user = userRepo.findByEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			userRepo.save(user);

			String toAddress = user.getEmail();
			String fromAddress = "vythanhlam100@gmail.com";
			String senderName = "GHTK Profile";
			String subject = "Xác thực quên mật khẩu ";
			String content = "Chào [[name]],<br>" + "Vui lòng ấn vào đường link phía dưới để cài đặt lại mật khẩu:<br>"
					+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Nếu bạn không muốn cài đặt lại mật khẩu hoặc bạn có bất cứ thắc mắc gì khác, hãy gửi phản hồi lại cho chúng tôi. Cảm ơn bạn,<br>" + "GHTK Profile.";
			
			
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getUsername());
			String verifyURL = "http://localhost:3000" + "/verifyForgotpassword?code=" + user.getResetPasswordToken();

			content = content.replace("[[URL]]", verifyURL);

			helper.setText(content, true);

			emailSender.send(message);
			return new Status(true, "Email hợp lệ, chúng tôi đã gửi email xác thực lại mật khẩu cho bạn, vui lòng kiểm tra hộp thư.");
		} else {
			return new Status(false, "Không tìm thấy email");
		}

	}

	// Gửi Email xác thực đăng kí tài khoản
	private void sendVerificationEmail(UserEntity user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "vythanhlam100@gmail.com";
		String senderName = "GHTK Profile";
		String subject = "Xác thực đăng kí tài khoản ";
		String content = "Chào [[name]],<br>" + "Vui lòng ấn vào đường link phía dưới để xác thực tài khoản:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Cảm ơn bạn,<br>" + "GHTK Profile.";

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getUsername());
		String verifyURL = siteURL + "/account?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		emailSender.send(message);

	}

	// XÁC THỰC EMAIL
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





	public Status updatePassword( String newPassword, String resetPasswordToken) {
		UserEntity userForgotP = userRepo.findByResetPasswordToken(resetPasswordToken);
		
		if(userForgotP != null) {
		
//			String encodedPassword = passwordEncoder.encode(newPassword);
//			userForgotP.setPassword(encodedPassword);
			
			userForgotP.setPassword(passwordEncoder.encode(newPassword));
			userForgotP.setResetPasswordToken(null);
			userRepo.save(userForgotP);
			return new Status(true,"Cập nhật mật khẩu thành công");
		}return new Status(false, "Đường dẫn không hợp lệ");
			
		
	}
	
	public LoginRequest checkStatusAccount(LoginRequest login) {
		String username = login.getUsername();
		UserEntity user = userRepo.findByUsername(username);
		if(user != null) {
			if (!user.isStatus() ) {
				login.setPassword("1");
			}
		}
		return login;
	}
	
	public boolean checkFirstLogin(LoginRequest login) {
		String username = login.getUsername();
		UserEntity user = userRepo.findByUsername(username);
		
		ProfileEntity profile = profileRepo.findByUserId(user.getId());
		if(profile == null) {
			return true;
		}else {
			if(profile.getName() == null) {
				return true;
			}
		}
		return false;
	}


	public void createAccountFromSocial(CustomOAuth2User oAuth2User){
		String email = oAuth2User.getEmail();
		UserEntity userExist = userRepo.findByEmail(email);
		if(userExist == null){

			UserEntity user = new UserEntity();
			ProfileEntity profile = new ProfileEntity();
			user.setEmail(oAuth2User.getEmail());
			user.setEnabled(true);
			profile.setName(oAuth2User.getName());
			user.setProvide(oAuth2User.getOauth2ClientName());
			user.setRole("ROLE_USER");
			user.setStatus(true);
			userRepo.save(user);
			idTemp = user.getId();
			profile.setUserId(user.getId());
			profileRepo.save(profile);
		}else{
			idTemp = userExist.getId();
		}



	}
	public Boolean checkFirstLoginFromSocial(Long id){
		Optional<UserEntity> user = userRepo.findById(id);
		if(user.isPresent()){
			if(user.get().getPassword() == null){
				return true;
			}
		}return false;
	}


	public LoginResponse loginFromSocial(){
		SecurityContextHolder.getContext().setAuthentication((Authentication) authen);

		String jwt = tokenProvider.generateTokenByIdUser(idTemp);
		boolean check = checkFirstLoginFromSocial(idTemp);
		return new LoginResponse(jwt, check, null);
	}
}
