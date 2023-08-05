package final_backend.Board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import final_backend.Board.model.Board;
import final_backend.Board.service.BoardService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/boards")
public class BoardController {
    private static final int DEFAULT_SIZE=15;
    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/page/{page}")
    public List<Board> getBoards(Long cursorId, Integer size, @PathVariable int page){
        if (size==null) size=DEFAULT_SIZE;
        List<Board> pagedBoardList=(boardService.getBoard(cursorId, PageRequest.of(page=page,size))).getValues();
        return pagedBoardList;
    }

    @GetMapping("/{bid}")
    public ResponseEntity<Board> getBoardById(@PathVariable("bid") Long bid) {
        Optional<Board> board = boardService.getBoardById(bid);
        boardService.increaseViewCount(board.get());
        return board.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board createdBoard = boardService.createBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("bid") Long bid) {
        boardService.deleteBoard(bid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bid}")
    public ResponseEntity<Board> updateBoard(@PathVariable("bid") Long bid, @RequestBody Board updatedBoard) {
        Board updatedBoardEntity = boardService.updateBoard(bid, updatedBoard);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bid}/recommend")
    public ResponseEntity<Board> recommendBoard(@PathVariable("bid") Long bid) {
        Board updatedBoardEntity = boardService.recommendIncrease(bid);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
