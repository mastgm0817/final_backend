package final_backend.Wishlist.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RandomWishlistDTO {
    private String nickname;
    private List<Course> results;
}
