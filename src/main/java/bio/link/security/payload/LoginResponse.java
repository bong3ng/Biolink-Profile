package bio.link.security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	
    private String accessToken;
    private String tokenType = "Bearer";
    private Boolean firstLogin;
    private String username;

    public LoginResponse(String accessToken, Boolean firstLogin, String username) {
        this.accessToken = accessToken;
        this.firstLogin = firstLogin;
        this.username = username;
    }
}
