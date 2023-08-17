package final_backend.Board.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import final_backend.Comments.model.Comment;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String uid;
    private String nickName;

    private String bTitle;
    private String bContent;
    private LocalDateTime b_createdAt;
    private LocalDateTime b_updatedAt;
    private Long b_views;
    private Long comments;
    private Long b_recommendations;
//
    @JsonManagedReference
    @OneToMany(mappedBy = "boardDTO", cascade = CascadeType.ALL)
    private List<Comment> commentList;


}