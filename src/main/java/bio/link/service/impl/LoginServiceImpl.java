package bio.link.service.impl;

import bio.link.model.dto.LoginResponseDto;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.ProfileRepository;
import bio.link.repository.UserRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.oauth2.CustomOAuth2User;
import bio.link.security.payload.LoginRequest;
import bio.link.security.payload.LoginResponse;
import bio.link.security.payload.Status;
import bio.link.security.user.CustomUserDetails;
import bio.link.service.LoginService;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;

import static bio.link.constant.GlobalConstant.MY_URL_WEBSITE;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepo;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    ProfileRepository profileRepo;

    @Autowired
    private JavaMailSender emailSender;


    @Autowired
    JwtTokenProvider tokenProvider;





    private static Authentication authen;
    private static Long idTemp;


    @Override
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

            try {
                // Tạo verificationCode
                String randomCode = RandomString.make(64);
                user.setVerificationCode(randomCode);
                user.setEnabled(false);

                userRepo.save(user);
                sendVerificationEmail(user, MY_URL_WEBSITE);

                message.setMessage("Chúng tôi đã gửi email xác thực tới địa chỉ mail của bạn, vui lòng kiểm tra hộp thư để kích hoạt tài khoản");
                message.setSuccess(true);
            } catch (UnsupportedEncodingException e) {


                e.printStackTrace();
            } catch (MessagingException e) {


                e.printStackTrace();
            }
        }

        return message;
    }

    @Override
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
            String verifyURL = MY_URL_WEBSITE + "/verifyForgotpassword?code=" + user.getResetPasswordToken();

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
    @Override
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


    @Override
    public Status updatePassword( String newPassword, String resetPasswordToken) {
        UserEntity userForgotP = userRepo.findByResetPasswordToken(resetPasswordToken);

        if(userForgotP != null) {

            userForgotP.setPassword(passwordEncoder.encode(newPassword));
            userForgotP.setResetPasswordToken(null);
            userRepo.save(userForgotP);
            return new Status(true,"Cập nhật mật khẩu thành công");
        }return new Status(false, "Đường dẫn không hợp lệ");


    }
    @Override
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
    @Override
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

    public Boolean checkFirstLoginFromSocial(Long id){
        Optional<UserEntity> user = userRepo.findById(id);
        if(user.isPresent()){
            if(user.get().getPassword() == null){
                return true;
            }
        }return false;
    }

    @Override
    public LoginResponseDto loginFromSocial(){
        SecurityContextHolder.getContext().setAuthentication((Authentication) authen);

        String jwt = tokenProvider.generateTokenByIdUser(idTemp);
        boolean check = checkFirstLoginFromSocial(idTemp);
        LoginResponse login = new LoginResponse(jwt, check, null);
        return new LoginResponseDto(true,"Đăng nhập thành công", login);
    }

    public String checkLoginAccount(String username){
        UserEntity user = userRepo.findByUsername(username);
        if(user == null){
            return "Tài khoản đăng nhập không hợp lệ, vui lòng kiểm tra lại";
        }else{
            if(!user.isStatus()){
                return "Tài khoản của bạn đang bị khóa, vui lòng liên hệ với Admin để được hỗ trợ";
            }if(!user.isEnabled()){
                return "Tài khoản của bạn chưa xác thực, vui lòng kiểm tra email";
            }return "Mật khẩu chưa chính xác, vui lòng kiểm tra lại";

        }
    }
    @Override
    public Status createFirstLoginFromSocial(String username, String password, String jwt){
        Optional<UserEntity> opt = userRepo.findById(tokenProvider.getUserIdFromHeader(jwt));

        if(opt.isPresent()){
            UserEntity user = opt.get();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
            return new Status(true, "Tạo tài khoản thành công");
        }
        return new Status(false, "Tạo tài khoản thất bại");
    }
    @Override
    public void createAccountFromSocial(CustomOAuth2User oAuth2User, Authentication authentication) {
        String email = oAuth2User.getEmail();
        UserEntity userExist = userRepo.findByEmail(email);
        authen = authentication;
        if (userExist == null) {
            UserEntity user = new UserEntity();


            LocalDate nowTime = LocalDate.now();
            user.setCreatedAt(nowTime);
            user.setEmail(oAuth2User.getEmail());
            user.setEnabled(true);
            user.setProvide(oAuth2User.getOauth2ClientName());
            userRepo.save(user);


            ProfileEntity profile = new ProfileEntity();
            profile.setName(oAuth2User.getName());

            idTemp = user.getId();
            profile.setUserId(user.getId());
            profileRepo.save(profile);
        } else {
            idTemp = userExist.getId();
        }

    }

}
