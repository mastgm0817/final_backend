package final_backend.Calendar.service;

import final_backend.Calendar.model.MyScheduleDTO;
import final_backend.Calendar.repository.MyScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MyScheduleService {
    @Autowired
    private MyScheduleRepository myScheduleRepository;

    public MyScheduleDTO CreateMySchedule(String userId, LocalDate date, String schedule, boolean share) {
        MyScheduleDTO mySchedule = new MyScheduleDTO();
        mySchedule.setWriterId(userId);
        mySchedule.setMyScheduleDate(date);
        mySchedule.setMyScheduleContent(schedule);
        mySchedule.setShared(share);
        return myScheduleRepository.save(mySchedule);
    }

    public List<MyScheduleDTO> getScheduleByUserId(String userId){
        return myScheduleRepository.findByWriterId(userId);
    }

    public MyScheduleDTO findById(Long scheduleId) {
        return myScheduleRepository.findById(scheduleId).orElse(null);
    }

    public void deleteMySchedule (Long scheduleId) {
        myScheduleRepository.deleteById(scheduleId);
    }
}
