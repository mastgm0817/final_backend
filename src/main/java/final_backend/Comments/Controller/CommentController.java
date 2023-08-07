package final_backend.Comments.Controller;

import final_backend.Board.model.Board;
import final_backend.Board.service.BoardService;
import final_backend.Comments.Service.CommentService;
import final_backend.Comments.model.Comment;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/boards/{bid}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByBoardId(@PathVariable("bid") Long boardId, Model model) {

        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        model.addAttribute("comments", comments);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable("bid") Long bid, @RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(bid, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<Void> deleteComment(@PathVariable("cid") Long cid) {
        commentService.deleteComment(cid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cid}/update")
    public ResponseEntity<Comment> updateComment(@PathVariable("cid") Long cid,
                                                 @RequestBody Comment updatedComment) {
        Comment updatedCommentEntity = commentService.updateComment(cid, updatedComment);
        return ResponseEntity.ok(updatedCommentEntity);
    }

}