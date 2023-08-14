package final_backend.admin.VisitCount.service;

import final_backend.admin.VisitCount.model.VisitCounterDTO;

import java.util.List;

public interface VisitCountService {
    List<VisitCounterDTO> findAll();
}
