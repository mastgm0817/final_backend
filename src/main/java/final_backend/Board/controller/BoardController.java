package final_backend.Board.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getAllPosts() {
        List<Board> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{bid}")
    public ResponseEntity<Board> getPostById(@PathVariable("bid") Long bid) {
        Optional<Board> board = boardService.getBoardById(bid);
        boardService.increaseViewCount(board.get());
        return board.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Board> createPost(@RequestBody Board board) {
        Board createdBoard = boardService.createBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> deletePost(@PathVariable("bid") Long bid) {
        boardService.deleteBoard(bid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bid}")
    public ResponseEntity<Board> updatePost(@PathVariable("bid") Long bid, @RequestBody Board updatedBoard) {
        Board updatedBoardEntity = boardService.updateBoard(bid, updatedBoard);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bid}/recommend")
    public ResponseEntity<Board> recommendPost(@PathVariable("bid") Long bid) {
        Board updatedBoardEntity = boardService.recommendIncrease(bid);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    public ResponseEntity<Board> viewIncrease(@PathVariable("bid") Long bid) {
//        Board updatedBoardEntity = boardService.increaseViewCount(getPostById(bid));
//        if (updatedBoardEntity != null) {
//            return ResponseEntity.ok(updatedBoardEntity);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
