package final_backend.Member.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserClickResponse {
    private int remainingClicks;
    private String message;

}