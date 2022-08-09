package bio.link.model.dto;



import bio.link.security.payload.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private Boolean success;
    private String message;
    private LoginResponse loginResponse;

}
