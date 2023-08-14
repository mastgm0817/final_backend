package final_backend.Board.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import final_backend.Board.model.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByBidDesc(Pageable page);

    List<Board> findByBidLessThanOrderByBidDesc(Long bid, Pageable page);

    Boolean existsByBidLessThan(Long bid);

    List<Board> findAllByNickNameContainingOrderByBidDesc(String b_content, Pageable pageable);


    List<Board> findAllByBContentContainingOrderByBidDesc(String nickName, Pageable pageable);


    List<Board> findAllByBTitleContainingOrderByBidDesc(String b_title, Pageable pageable);

}