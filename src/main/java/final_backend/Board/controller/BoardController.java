package final_backend.Board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import final_backend.Board.model.Board;
import final_backend.Board.service.BoardServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/boards")
public class BoardController {
    private static final int DEFAULT_SIZE=15;
    @Autowired
    private BoardServiceImpl boardServiceImpl;

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardServiceImpl.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<List<Board>> getBoards(Long cursorId, Integer size,
                                                 @PathVariable int page,
                                                 @RequestParam(value="findingMethod", required = false) String findingMethod,
                                                 @RequestParam(value="findStr", required = false) String findStr
    ) {
        if (size == null) size = DEFAULT_SIZE;
        if (findingMethod==null) {findingMethod="all";}
        List<Board> pagedBoardList = new ArrayList<>();
        if (cursorId == null) cursorId = boardServiceImpl.getDefaultCursorId();
        if (findStr == null) {
            pagedBoardList = (boardServiceImpl.getBoard(cursorId, PageRequest.of(page = page, size))).getValues();
        }
        else{
            System.out.println("controller:"+findingMethod);
            System.out.println("controller:"+findStr);
            pagedBoardList = (boardServiceImpl.getBoard(cursorId, PageRequest.of(page = page, size), findStr, findingMethod)).getValues();
        }
        return ResponseEntity.ok(pagedBoardList);
    }

    @GetMapping("/{bid}")
    public ResponseEntity<Board> getBoardById(@PathVariable("bid") Long bid) {
        Optional<Board> board = boardServiceImpl.getBoardById(bid);
        boardServiceImpl.increaseViewCount(board.get());
        return board.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board createdBoard = boardServiceImpl.createBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("bid") Long bid) {
        boardServiceImpl.deleteBoard(bid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bid}")
    public ResponseEntity<Board> updateBoard(@PathVariable("bid") Long bid, @RequestBody Board updatedBoard) {
        Board updatedBoardEntity = boardServiceImpl.updateBoard(bid, updatedBoard);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bid}/recommend")
    public ResponseEntity<Board> recommendBoard(@PathVariable("bid") Long bid) {
        Board updatedBoardEntity = boardServiceImpl.recommendIncrease(bid);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
