package final_backend.Comments.Controller;

import final_backend.Board.service.BoardServiceImpl;
import final_backend.Comments.Service.CommentService;
import final_backend.Comments.model.Comment;
import final_backend.Comments.model.CommentRequest;
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
    private CommentService commentService;
    @Autowired
    private BoardServiceImpl boardServiceImpl;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByBoardId(@PathVariable("bid") Long boardId, Model model) {

        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        model.addAttribute("comments", comments);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable("bid") Long bid, @RequestBody CommentRequest commentRequest) {
        Comment comment=commentRequest.getComment();
        String uid=commentRequest.getUid().replace("\"", "");
        Comment createdComment = commentService.createComment(bid, comment,uid);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<Void> deleteComment(@PathVariable("cid") Long cid, @RequestBody String uid) throws Exception {
        String realuid=uid.replace("\"", "");
        commentService.deleteComment(cid,realuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cid}/update")
    public ResponseEntity<Comment> updateComment(@PathVariable("cid") Long cid,
                                                 @RequestBody CommentRequest commentRequest) throws Exception {
        Comment comment=commentRequest.getComment();
        String uid=commentRequest.getUid().replace("\"", "");
        Comment updatedCommentEntity = commentService.updateComment(cid, comment,uid);
        return ResponseEntity.ok(updatedCommentEntity);
    }

}