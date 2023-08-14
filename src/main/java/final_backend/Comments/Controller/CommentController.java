package final_backend.Comments.Controller;

import final_backend.Board.service.BoardService;
import final_backend.Comments.Service.CommentServiceImpl;
import final_backend.Comments.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/boards/{bid}/comments")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByBoardId(@PathVariable("bid") Long boardId, Model model) {

        List<Comment> comments = commentServiceImpl.getCommentsByBoardId(boardId);
        model.addAttribute("comments", comments);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable("bid") Long bid, @RequestBody Comment comment) {
        Comment createdComment = commentServiceImpl.createComment(bid, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<Void> deleteComment(@PathVariable("cid") Long cid) {
        commentServiceImpl.deleteComment(cid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cid}/update")
    public ResponseEntity<Comment> updateComment(@PathVariable("cid") Long cid,
                                                 @RequestBody Comment updatedComment) {
        Comment updatedCommentEntity = commentServiceImpl.updateComment(cid, updatedComment);
        return ResponseEntity.ok(updatedCommentEntity);
    }

}