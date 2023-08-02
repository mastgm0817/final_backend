package final_backend.Comments.Controller;

import final_backend.Board.model.Board;
import final_backend.Board.service.BoardService;
import final_backend.Comments.Service.CommentService;
import final_backend.Comments.model.Comment;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{bid}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;
//
//    @GetMapping
//    public String getCommentsByBoardId(@PathVariable("bid") Long bid, Model model) {
//        List<Comment> comments = commentService.findCommentsByBoardId(bid);
//        model.addAttribute("comments", comments);
//        return "/api/boards/{bid}/comments";
//    }
//
//    @PostMapping
//    public String createComment(@PathVariable("bid") Long bid, @RequestBody Comment comment) {
//        if (boardService.getBoardById(bid)!=null){
//            comment.setBoard(boardService.getBoardById(bid).get());
//            commentService.saveComment(bid, comment);
//        }
//        return "redirect:/api/boards/{bid}/comments";
//
//
////        Board createdBoard = boardService.createBoard(board);
////        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
//
//    }

//    @PostMapping
//    public String createComment(@PathVariable Long bid, @ModelAttribute Comment comment) {
//        comment.setBoard(boardService.getBoardById(bid).get());
//        commentService.saveComment(bid, comment);
//        return "redirect:/bDetail/" + bid;
//    }

//    @GetMapping
//    public String getCommentsByBoardId(@PathVariable("id") Long boardId, Model model) {
//        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
//        model.addAttribute("comments", comments);
//        return "comments";
//    }

//    @PutMapping("/{cid}/update")
//    public ResponseEntity<Comment> updateComment(@PathVariable("cid") Long cid,
//                                                 @RequestBody Comment updatedComment) {
//        commentService.updateComment(cid, updatedComment);
//    }

//    @DeleteMapping("/{cid}")
//    public ResponseEntity<Void> deleteComment(@PathVariable("cid") Long cid) {
//        commentService.deleteComment(cid);
//        return "redirect:/api/boards/{bid}/comments";
//    }
}

