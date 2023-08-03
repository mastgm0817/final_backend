package final_backend.Comments.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import final_backend.Board.model.Board;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String cContent;
    private LocalDateTime cCreatedAt;
    private String uid;
    private String nickName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid")
    private Board boardDTO;

}
