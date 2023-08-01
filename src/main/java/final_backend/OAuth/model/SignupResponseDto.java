package final_backend.OAuth.model;

import final_backend.Member.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDto {
    private User user;
    private String accessToken;
    private boolean signupSuccess;
}