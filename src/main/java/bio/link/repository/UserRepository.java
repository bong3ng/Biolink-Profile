package bio.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bio.link.model.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	UserEntity findByEmail(String email);

	@Query("SELECT u FROM UserEntity u WHERE u.verificationCode = ?1")
	public UserEntity findByVerificationCode(String code);

    
    UserEntity findByResetPasswordToken(String token);


}
