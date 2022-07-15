package bio.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import bio.link.user.UserEntity;
import bio.link.user.UserRepository;

@SpringBootApplication
public class BioJwtApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BioJwtApplication.class, args);
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
	        user.setUsername("bong");
	        user.setPassword(passwordEncoder.encode("test123"));
	        user.setEmail("lam@gmail.com");
	        user.setRole("ROLE_USER");
	        userRepository.save(user);
	        System.out.println(user);
	    }
}
