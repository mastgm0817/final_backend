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

    Page<Board> findByBContentContainingOrderByBidDesc(String b_content, Pageable pageable);


    Page<Board> findByNickNameContainingOrderByBidDesc(String nickName, Pageable pageable);


    Page<Board> findByBTitleContainingOrderByBidDesc(String b_title, Pageable pageable);

}