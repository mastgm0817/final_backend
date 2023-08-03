package final_backend.Comments.Service;

import final_backend.Board.model.Board;
import final_backend.Board.respository.BoardRepository;
import final_backend.Board.service.BoardService;
import final_backend.Comments.Repository.CommentRepository;
import final_backend.Comments.model.Comment;
import org.hibernate.WrongClassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    public List<Comment> getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardDTOBid(boardId);
        return comments;
    }

//    public Comment findCommentByCid(Long cid){
//        return commentRepository.findCommentByCid(cid);
//    }
//
    public Comment createComment(Long bid, Comment comment) {
        Board board=boardRepository.findById(bid).
                orElseThrow(() -> new EntityNotFoundException(bid+" Board not found"));
        comment.setBoardDTO(board);
        System.out.println(comment.getCContent());
        comment.setCCreatedAt(LocalDateTime.now());
        comment.setUid("lin");
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long cid, Comment updatedComment) {
        Optional<Comment> commentOptional = commentRepository.findCommentByCid(cid);
        Comment existingComment;
        if (commentOptional.isPresent()) {
            existingComment = commentOptional.get();
            existingComment.setCContent(updatedComment.getCContent());
        } else {
            return updatedComment;
        }
        return commentRepository.save(existingComment);

    }

    public void deleteComment(Long cid) {
        commentRepository.deleteById(cid);
    }

}
