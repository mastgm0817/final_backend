package final_backend.admin.VisitCount.controller;

import final_backend.admin.VisitCount.model.VisitCounterDTO;
import final_backend.admin.VisitCount.service.VisitCountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/counter")
public class VisitCountController {
    @Autowired
    private VisitCountServiceImpl visitCountServiceImpl;
    @GetMapping
    public ResponseEntity<List<VisitCounterDTO>> GetVisitCount(){
        return ResponseEntity.ok(visitCountServiceImpl.findAll());
    }
}
