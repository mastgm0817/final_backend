package final_backend.Comments.model;

import lombok.Data;
import final_backend.Board.model.Board;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String content;
    private String uid;
    private String nickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid")
    private Board boardDTO;
}
