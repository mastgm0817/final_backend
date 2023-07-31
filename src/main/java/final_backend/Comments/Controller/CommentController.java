package final_backend.Comments.Controller;

import final_backend.Board.service.BoardService;
import final_backend.Comments.Service.CommentService;
import final_backend.Comments.model.Comment;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;

    //R
    @GetMapping("/board/detail/{id}/comments")
    public String getCommentsByBoardId(@PathVariable("id") Long boardId, Model model) {
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("/board/detail/{id}/comments")
    public Comment createComment(@PathVariable("id") Long id, @ModelAttribute Comment comment){
        comment.setBoardDTO(boardService.getBoardById(id));

        return comment;}

}
