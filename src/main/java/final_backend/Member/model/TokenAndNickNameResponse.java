package final_backend.Member.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenAndNickNameResponse {
    private String token;
    private String nickName;
    private String userRole;
    public TokenAndNickNameResponse(String token, String nickName, String userRole) {
        this.token = token;
        this.nickName = nickName;
        this.userRole = userRole;
    }
}
