package final_backend.Calendar.service;

import final_backend.Calendar.model.ScheduleDTO;
import final_backend.Calendar.repository.ScheduleRepository;
import final_backend.Member.model.User;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserService userService;

    public ScheduleDTO CreateMySchedule(String userName, LocalDate date, String schedule, boolean share, String loverName) {
        ScheduleDTO mySchedule = new ScheduleDTO();
        mySchedule.setWriterId(userName);
        mySchedule.setScheduleDate(date);
        mySchedule.setScheduleContent(schedule);
        mySchedule.setShared(share);

        User lover = userService.findByNickName(loverName);
        mySchedule.setLover(lover);

        return scheduleRepository.save(mySchedule);
    }

    public List<ScheduleDTO> getScheduleByUserName(String userName){
        return scheduleRepository.findByWriterId(userName);
    }

    public ScheduleDTO findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    public void deleteMySchedule (Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public List<ScheduleDTO> getScheduleByNickName(String nickname) {
        return scheduleRepository.findByWriterId(nickname);
    }
}
