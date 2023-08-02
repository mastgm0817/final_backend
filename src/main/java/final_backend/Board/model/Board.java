package final_backend.Board.model;

import final_backend.Comments.model.Comment;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String uid;
    private String nickName;

    private String b_title;
    private String b_content;
    private LocalDateTime b_createdAt;
    private LocalDateTime b_updatedAt;
    private Long b_views;
    private Long comments;
    private Long b_recommendations;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> commentList;
    /* 댓글의 postDTO와 맵핑 */


}