package final_backend.Comments.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import final_backend.Board.model.Board;
import final_backend.Comments.Repository.CommentRepository;
import final_backend.Comments.model.Comment;
import final_backend.Comments.Service.CommentServiceImpl;
import final_backend.Board.respository.BoardRepository;
import final_backend.Board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CommentServiceImplTest {

    private CommentServiceImpl commentService;
    private CommentRepository commentRepository;
    private BoardRepository boardRepository;
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        boardRepository = mock(BoardRepository.class);
        boardService = mock(BoardService.class);
        commentService = new CommentServiceImpl(commentRepository, boardRepository, boardService);
    }

    @Test
    void getCommentsByBoardId_ValidBoardId_ReturnsListOfComments() {
        Long boardId = 1L;
        Comment comment = new Comment();
        when(commentRepository.findByBoardDTOBid(boardId)).thenReturn(Collections.singletonList(comment));

        List<Comment> result = commentService.getCommentsByBoardId(boardId);

        assertEquals(1, result.size());
        assertEquals(comment, result.get(0));
    }

    @Test
    void createComment_ValidData_ReturnsCreatedComment() {
        Long boardId = 1L;
        Board board = new Board();
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        Comment comment = new Comment();
        comment.setCContent("Test Content");
        comment.setCCreatedAt(LocalDateTime.now());
        comment.setUid("lin");

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment result = commentService.createComment(boardId, comment);

        assertNotNull(result);
        assertEquals(comment.getCContent(), result.getCContent());
        assertEquals(comment.getCCreatedAt(), result.getCCreatedAt());
        assertEquals(comment.getUid(), result.getUid());
        assertEquals(board, result.getBoardDTO());
    }

    @Test
    void updateComment_ValidComment_ReturnsUpdatedComment() {
        Long cid = 1L;
        Comment existingComment = new Comment();
        existingComment.setCContent("Existing Content");

        Comment updatedComment = new Comment();
        updatedComment.setCContent("Updated Content");

        when(commentRepository.findCommentByCid(cid)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment result = commentService.updateComment(cid, updatedComment);

        assertNotNull(result);
        assertEquals(updatedComment.getCContent(), result.getCContent());
    }

    @Test
    void updateComment_NonExistentComment_ReturnsUpdatedComment() {
        Long cid = 1L;
        Comment updatedComment = new Comment();
        updatedComment.setCContent("Updated Content");

        when(commentRepository.findCommentByCid(cid)).thenReturn(Optional.empty());

        Comment result = commentService.updateComment(cid, updatedComment);

        assertNotNull(result);
        assertEquals(updatedComment.getCContent(), result.getCContent());
    }

    @Test
    void deleteComment_ValidComment_DeletesComment() {
        Long cid = 1L;
        commentService.deleteComment(cid);

        verify(commentRepository, times(1)).deleteById(cid);
    }
}
