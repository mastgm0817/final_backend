package final_backend.Member.model;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class UserCredentialResponse {

    private Long uid;
    private String nickName;
    private String email;
    private String profileImage;


}
