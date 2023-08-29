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
    private LocalDateTime bCreatedAt;
    private LocalDateTime bUpdatedAt;
    private Long bViews;
    private Long comments;
    private Long bRecommendations;
//
    @JsonManagedReference
    @OneToMany(mappedBy = "boardDTO", cascade = CascadeType.ALL)
    private List<Comment> commentList;


}