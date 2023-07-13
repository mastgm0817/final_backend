package final_backend.Calendar.repository;

import final_backend.Calendar.model.ShareScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareScheduleRepository extends JpaRepository<ShareScheduleDTO,Long> {

    List<ShareScheduleDTO> findByShareScheduleWriterIdOrShareScheduleLoverId(String userId, String LoverId);

    List<ShareScheduleDTO> findByShareScheduleWriterIdAndShareScheduleLoverId(String writerId, String loverId);

}
