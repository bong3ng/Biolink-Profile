package bio.link.model.dto;

import bio.link.security.payload.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
	private Boolean success;
	private String message;
	private LoginResponse loginResponse;

}
