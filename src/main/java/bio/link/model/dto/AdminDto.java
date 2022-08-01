package bio.link.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
	private Long id;
	private String username;
	private String email;
	private String name;
	private String bio;
	private Boolean status;
	private Boolean enabled;
	private LocalDate createdAt;
	private String role;
	
}
