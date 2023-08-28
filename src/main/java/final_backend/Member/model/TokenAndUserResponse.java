package final_backend.Member.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenAndUserResponse {
    private String token;
    private User user;

    public TokenAndUserResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
