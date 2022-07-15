package bio.link.user;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "user_abc")
public class UserEntity {
	@Id
	@GeneratedValue()
	private Long id;
	
	@Column(nullable = false, unique = true, length = 45)
	private String username;
	private String password;
	@Column(nullable = false, unique = true, length = 45)
	private String email;
	private String role;
	@Column(name = "create_at")
	private LocalDate createdAt;
	
	@Column(name = "modified_at")
	private LocalDate modifiedAt;
}
