package final_backend.Calendar.repository;

import final_backend.Calendar.model.MyScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MyScheduleRepository extends JpaRepository<MyScheduleDTO, Long> {
    List<MyScheduleDTO> findByWriterId(String userId);
}
