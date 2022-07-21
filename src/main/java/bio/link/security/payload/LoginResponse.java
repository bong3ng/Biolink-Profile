package bio.link.security.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Boolean firstLogin;

    public LoginResponse(String accessToken, Boolean firstLogin) {
        this.accessToken = accessToken;
        this.firstLogin = firstLogin;
    }
}
