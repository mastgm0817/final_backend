package final_backend.Board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import final_backend.Board.model.Board;
import final_backend.Board.respository.BoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> getAllPosts() {
        return boardRepository.findAll();
    }

    public Optional<Board> getPostById(Long bid) {
        return boardRepository.findById(bid);
    }

    public Board createPost(Board board) {
        LocalDateTime currentTime = LocalDateTime.now();
        board.setB_createdAt(currentTime);
        board.setB_updatedAt(currentTime);
        board.setB_views(0L);
        board.setB_recommendations(0L);
        return boardRepository.save(board);
    }

    public void deletePost(Long bid) {
        boardRepository.deleteById(bid);
    }

    public Board updatePost(Long bid, Board updatedBoard) {
        Optional<Board> postOptional = boardRepository.findById(bid);
        if (postOptional.isPresent()) {
            Board existingBoard = postOptional.get();
            existingBoard.setB_title(updatedBoard.getB_title());
            existingBoard.setB_content(updatedBoard.getB_content());
            existingBoard.setUid(updatedBoard.getUid());
            existingBoard.setB_updatedAt(LocalDateTime.now());
            return boardRepository.save(existingBoard);
        } else {
            return null;
        }
    }

    public Board increaseViewCount(Board board) {
        board.setB_views(board.getB_views() + 1);
        return boardRepository.save(board);
    }

    public Board recommendIncrease(Long bid) {
        Optional<Board> postOptional = boardRepository.findById(bid);
        if (postOptional.isPresent()) {
            Board existingBoard = postOptional.get();
            existingBoard.setB_recommendations(existingBoard.getB_recommendations()+1);
            return boardRepository.save(existingBoard);
        } else {
            return null;
        }
    }
}