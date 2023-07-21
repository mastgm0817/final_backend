package final_backend.Board.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import final_backend.Board.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
