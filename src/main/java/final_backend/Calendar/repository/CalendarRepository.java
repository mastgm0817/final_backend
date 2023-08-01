package final_backend.Calendar.repository;

import final_backend.Calendar.model.CalendarDTO;
import final_backend.Member.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarDTO, Long> {
    List<CalendarDTO> findByWriterId(String userId);
    List<CalendarDTO> findByLoverAndSharedTrue(User lover);

}
