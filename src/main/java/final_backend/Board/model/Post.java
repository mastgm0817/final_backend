package final_backend.Board.model;

import lombok.Data;
import final_backend.Comments.model.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long comments; /* 댓글 달린 갯수 */
    private Long views; /* 조회수 */
    private Long recommendations; /* 추천수 */

//    /* 댓글의 postDTO와 맵핑 */
//    @OneToMany(mappedBy = "postDTO", cascade = CascadeType.ALL)
//    private List<Comment> commentList;

}