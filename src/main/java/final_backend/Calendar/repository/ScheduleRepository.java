package final_backend.Calendar.repository;

import final_backend.Calendar.model.ScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleDTO, Long> {
    List<ScheduleDTO> findByWriterId(String userId);

    List<ScheduleDTO> findByLoverAndSharedTrue(String lover);
}
