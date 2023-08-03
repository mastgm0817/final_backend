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

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardById(Long bid) {
        return boardRepository.findById(bid);
    }

    public Board createBoard(Board board) {
        LocalDateTime currentTime = LocalDateTime.now();
        board.setB_createdAt(currentTime);
        board.setB_updatedAt(currentTime);
        board.setB_views(0L);
        board.setB_recommendations(0L);
        return boardRepository.save(board);
    }

    public void deleteBoard(Long bid) {
        boardRepository.deleteById(bid);
    }

    public Board updateBoard(Long bid, Board updatedBoard) {
        Optional<Board> boardOptional = boardRepository.findById(bid);
        if (boardOptional.isPresent()) {
            Board existingBoard = boardOptional.get();
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
        System.out.println(board.getBid()+"조회됨");
        return boardRepository.save(board);
    }

    public Board recommendIncrease(Long bid) {
        Optional<Board> boardOptional = boardRepository.findById(bid);
        if (boardOptional.isPresent()) {
            Board existingBoard = boardOptional.get();
            existingBoard.setB_recommendations(existingBoard.getB_recommendations()+1);
            System.out.println(bid +" 번 게시글 추천됨");
            return boardRepository.save(existingBoard);
        } else {
            return null;
        }
    }

    public List<Board> getMyBoards(String nickname) {
        List<Board> myboards = boardRepository.findByNickName(nickname);
        return myboards;
    }

}