package final_backend.Comments.Service;

import final_backend.Comments.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByBoardId(Long boardId);

    //    public Comment findCommentByCid(Long cid){
    //        return commentRepository.findCommentByCid(cid);
    //    }
    //
    Comment createComment(Long bid, Comment comment);

    Comment updateComment(Long cid, Comment updatedComment);

    void deleteComment(Long cid);
}
