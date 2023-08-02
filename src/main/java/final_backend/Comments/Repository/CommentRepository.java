package final_backend.Comments.Repository;

import final_backend.Comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByBoard(Board board)
//    List<Comment> findByBoard(Long bid);
//    Comment findCommentByCid(Long cid);
//    List<Comment> findByBoardBid(Long boardId);
}
