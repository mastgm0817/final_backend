package final_backend.Member.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenAndNickNameResponse {
    private String token;
    private String nickName;

    public TokenAndNickNameResponse(String token, String nickName) {
        this.token = token;
        this.nickName = nickName;
    }
}
