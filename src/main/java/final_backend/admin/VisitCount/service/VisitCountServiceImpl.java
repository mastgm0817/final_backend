package final_backend.admin.VisitCount.service;

import final_backend.admin.VisitCount.model.VisitCounterDTO;
import final_backend.admin.VisitCount.repository.VisitCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitCountServiceImpl implements VisitCountService {

    private final VisitCounterRepository visitCounterRepository;

    public VisitCountServiceImpl(VisitCounterRepository visitCounterRepository) {
        this.visitCounterRepository = visitCounterRepository;
    }

    @Override
    public List<VisitCounterDTO> findAll(){
        return visitCounterRepository.findAll();
    }
}
