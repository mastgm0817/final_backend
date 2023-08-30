package final_backend.Wishlist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDeleteRequestDto {
    private String nickName;
    private List<Course> targetCourse;
}
