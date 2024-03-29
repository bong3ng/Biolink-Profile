package bio.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import bio.link.model.entity.UserEntity;



import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "SELECT username FROM users WHERE id = :userId" , nativeQuery = true)
	String getUsernameByUserId(
			@Param("userId") Long userId
	);

//	@Query(value = "SELECT user_id FROM users WHERE username = :username",nativeQuery = true)
//	Long getUserIdByUsername(
//			@Param("username") String username
//	);


	UserEntity findByUsername(String username);

	UserEntity findByEmail(String email);

	@Query("SELECT u FROM UserEntity u WHERE u.verificationCode = ?1")
	public UserEntity findByVerificationCode(String code);

    
    UserEntity findByResetPasswordToken(String token);

	@Query(value = "SELECT role FROM users WHERE id = :userId LIMIT 1" , nativeQuery = true)
	String getUserRoleById(@Param("userId") Long userId);

}
