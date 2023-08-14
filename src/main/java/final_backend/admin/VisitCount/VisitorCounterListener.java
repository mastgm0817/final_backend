package final_backend.admin.VisitCount;
import final_backend.admin.VisitCount.model.VisitCounterDTO;
import final_backend.admin.VisitCount.repository.VisitCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

@Component
public class VisitorCounterListener implements HttpSessionListener {

    @Autowired
    private VisitCounterRepository visitCounterRepository;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // 새로운 세션이 생성될 때 방문자 수 증가
        Date visitDate = new Date();
        VisitCounterDTO counter = visitCounterRepository.findByVisitDate(visitDate);
        if (counter == null) {
            counter = new VisitCounterDTO();
            counter.setVisitDate(visitDate);
            counter.setVisitorCount(1);
        } else {
            counter.setVisitorCount(counter.getVisitorCount() + 1);
        }
        visitCounterRepository.save(counter);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 세션이 만료될 때 필요한 작업 수행
    }
}