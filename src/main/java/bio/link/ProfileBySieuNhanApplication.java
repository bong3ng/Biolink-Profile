package bio.link;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import bio.link.model.entity.UserEntity;
import bio.link.repository.UserRepository;



@SpringBootApplication
public class ProfileBySieuNhanApplication implements CommandLineRunner {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProfileBySieuNhanApplication.class, args);
	}
	 @Autowired
	    UserRepository userRepository;
	    @Autowired
	    PasswordEncoder passwordEncoder;

	    @Override
	    public void run(String... args) throws Exception {
	        // Khi chương trình chạy
	        // Insert vào csdl một user.
	        UserEntity user = new UserEntity();
	        user.setUsername("123");
	        user.setPassword(passwordEncoder.encode("123"));
	        user.setEmail("lam@gmail.com");
	        user.setRole("ROLE_USER");
	        userRepository.save(user);
	        System.out.println(user);
	    }
}
