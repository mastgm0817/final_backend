package final_backend.Board.service;

import final_backend.Board.model.Board;
import final_backend.Board.model.CursorResult;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    Long getAllBoards();

    Optional<Board> getBoardById(Long bid);

    CursorResult<Board> getBoard(Long cursorId, Pageable page, String findStr, String findingMethod);

    CursorResult<Board> getBoard(Long cursorId, Pageable page);

    List<Board> getBoardList(Long cursorId, Pageable page, String findStr, String findingMethod);

    List<Board> getBoardList(Long bid, Pageable page);

    Boolean hasNext(Long bid);

    Board createBoard(Board board);

    void deleteBoard(Long bid);

    Board updateBoard(Long bid, Board updatedBoard);

    Board increaseViewCount(Board board);

    Board recommendIncrease(Long bid);
}
