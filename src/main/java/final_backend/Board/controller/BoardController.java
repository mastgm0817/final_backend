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
        List<Board> boards = boardService.getAllPosts();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{bid}")
    public ResponseEntity<Board> getPostById(@PathVariable("bid") Long bid) {
        Optional<Board> post = boardService.getPostById(bid);
        return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Board> createPost(@RequestBody Board board) {
        Board createdBoard = boardService.createPost(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> deletePost(@PathVariable("bid") Long bid) {
        boardService.deletePost(bid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bid}")
    public ResponseEntity<Board> updatePost(@PathVariable("bid") Long bid, @RequestBody Board updatedBoard) {
        Board updatedBoardEntity = boardService.updatePost(bid, updatedBoard);
        if (updatedBoardEntity != null) {
            return ResponseEntity.ok(updatedBoardEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
