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

    // 게시글과 댓글 간의 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid")
    private Board boardDTO;

}