package final_backend.Board.service;

import final_backend.Board.model.CursorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "bid"));
    }

    public Optional<Board> getBoardById(Long bid) {
        return boardRepository.findById(bid);
    }


    public CursorResult<Board> getBoard(Long cursorId, Pageable page, String findStr, String findingMethod){
        final List<Board> boards = getBoards(cursorId, page, findStr, findingMethod);
        final Long lastIdOfList = boards.isEmpty() ?
                null : boards.get(boards.size() - 1).getBid();

        return new CursorResult<Board>(boards, hasNext(lastIdOfList));
    }
//    public Page<Board> boardList(Pageable pageable, String findStr, String findingMethod) {
//        if ("nickname".equals(findingMethod)) {
//            return boardRepository.findByNickNameContainingOrderByBidDesc(findStr, pageable);
//        } else if ("content".equals(findingMethod)) {
//            return boardRepository.findByBContentContainingOrderByBidDesc(findStr, pageable);
//        } else if ("title".equals(findingMethod)) {
//            return boardRepository.findByBTitleContainingOrderByBidDesc(findStr, pageable);
//        } else{
//            return boardRepository.findAll(pageable);
//        }
//    }
    private List<Board> getBoards(Long bid, Pageable page, String findStr, String findingMethod) {

        if (bid == null){
            boardRepository.findAllByOrderByBidDesc(page);
        } else if ("nickname".equals(findingMethod)) {
            return boardRepository.findAllByNickNameContainingOrderByBidDesc(findStr, page);
        } else if ("content".equals(findingMethod)) {
            return boardRepository.findAllByBContentContainingOrderByBidDesc(findStr, page);
        } else if ("title".equals(findingMethod)) {
            return boardRepository.findAllByBTitleContainingOrderByBidDesc(findStr, page);
        }
        return boardRepository.findByBidLessThanOrderByBidDesc(bid, page);
    }

    private Boolean hasNext(Long bid) {
        if (bid == null) return false;
        return boardRepository.existsByBidLessThan(bid);
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
            existingBoard.setBTitle(updatedBoard.getBTitle());
            existingBoard.setBContent(updatedBoard.getBContent());
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


//    public List<Board> getMyBoards(String nickname) {
//        List<Board> myboards = boardRepository.findByNickName(nickname);
//        return myboards;
//    }

}