package final_backend.admin.VisitCount.repository;

import final_backend.admin.VisitCount.model.VisitCounterDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface VisitCounterRepository extends JpaRepository<VisitCounterDTO ,Long> {
    VisitCounterDTO findByVisitDate(Date visitDate);
}
