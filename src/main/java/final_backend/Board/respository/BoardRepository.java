package final_backend.Board.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import final_backend.Board.model.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
//    Page<Board> findByNickName(String nickname);
//    @Query(countQuery = "select board from board b where b.bid=:bid" )
//    Page<Board> findAll(Pageable pageable);

    List<Board> findAllByOrderByBidDesc(Pageable page);

    List<Board> findByBidLessThanOrderByBidDesc(Long bid, Pageable page);

    Boolean existsByBidLessThan(Long bid);

    List<Board> findByBTitleContainingAndBidLessThanOrderByBidDesc(String findStr, Long cursorId, Pageable page);

    List<Board> findByBContentContainingAndBidLessThanOrderByBidDesc(String findStr, Long cursorId, Pageable page);

    List<Board> findByNickNameContainingAndBidLessThanOrderByBidDesc(String findStr, Long cursorId, Pageable page);
}
