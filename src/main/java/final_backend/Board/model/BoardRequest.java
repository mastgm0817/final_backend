package final_backend.Board.model;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class BoardRequest {
    private Board board;
    private String uid;

    public Board getBoard() {
        return board;
    }

    public String getUid() {
        return uid;
    }
}
