package final_backend.Comments.Service;

import final_backend.Board.model.Board;
import final_backend.Board.respository.BoardRepository;
import final_backend.Board.service.BoardServiceImpl;
import final_backend.Comments.Repository.CommentRepository;
import final_backend.Comments.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    private BoardServiceImpl boardServiceImpl;

    public List<Comment> getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardDTOBid(boardId);
        return comments;
    }

//    public Comment findCommentByCid(Long cid){
//        return commentRepository.findCommentByCid(cid);
//    }
//
    public Comment createComment(Long bid, Comment comment,String uid) {
        Board board=boardRepository.findById(bid).
                orElseThrow(() -> new EntityNotFoundException(bid+" Board not found"));
        comment.setBoardDTO(board);
        System.out.println(comment.getCContent());
        comment.setCCreatedAt(LocalDateTime.now());
        comment.setUid(uid);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long cid, Comment updatedComment, String uid) throws Exception{
        Optional<Comment> commentOptional = commentRepository.findCommentByCid(cid);

        if (!commentOptional.isPresent()) {
            throw new Exception("댓글이 존재하지 않습니다.");
        }
        Comment existingComment = commentOptional.get();
        if (!uid.equals(existingComment.getUid())){
            throw new Exception("댓글의 작성자만 수정할 수 있습니다.");
        }
        existingComment.setCContent(updatedComment.getCContent());
        return commentRepository.save(existingComment);

    }

    public void deleteComment(Long cid, String uid) throws Exception{
        Optional<Comment> commentOptional=commentRepository.findCommentByCid(cid);
        if (!commentOptional.isPresent()) {
            throw new Exception("댓글을 찾을 수 없습니다.");
        }
        Comment comment = commentOptional.get();
        if (uid.equals(comment.getUid())) {
            commentRepository.deleteById(cid);
        }else {
            throw new Exception("댓글의 작성자만 삭제할 수 있습니다.");}
        }
    }

