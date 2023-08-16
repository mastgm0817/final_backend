package final_backend.Calendar.service;

import final_backend.Calendar.model.CalendarDTO;
import final_backend.Calendar.repository.CalendarRepository;
import final_backend.Member.model.User;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private UserService userService;

    @Autowired
    public CalendarServiceImpl(CalendarRepository calendarRepository, UserService userService) {
        this.calendarRepository = calendarRepository;
        this.userService = userService;
    }

    @Override
    public CalendarDTO CreateMySchedule(String userName, LocalDate date, String schedule, boolean share, String loverName) {
        CalendarDTO mySchedule = new CalendarDTO();
        mySchedule.setWriterId(userName);
        mySchedule.setScheduleDate(date);
        mySchedule.setScheduleContent(schedule);
        mySchedule.setShared(share);

        User lover = userService.findByNickName(loverName);
        mySchedule.setLover(lover);

        return calendarRepository.save(mySchedule);
    }

    @Override
    public List<CalendarDTO> getSharedSchedulesByLoverName(User loverName) {
        return calendarRepository.findByLoverAndSharedTrue(loverName);
    }


    @Override
    public CalendarDTO findById(Long scheduleId) {
        return calendarRepository.findById(scheduleId).orElse(null);
    }

    // 삭제 기능
    @Override
    public boolean deleteSchedule(Long scheduleId, String nickName, boolean shared) {
        CalendarDTO calendarDTO = calendarRepository.findById(scheduleId).orElse(null);

        if (calendarDTO == null) {
            return false;
        }

        boolean isWriter = calendarDTO.getWriterId().equals(nickName);
        boolean isSharedLover = shared && calendarDTO.getLover() != null && calendarDTO.getLover().getNickName().equals(nickName);

        if (!isWriter && !isSharedLover) {
            return false;
        }

        calendarRepository.deleteById(scheduleId);
        return true;
    }



    @Override
    public List<CalendarDTO> getScheduleByNickName(String nickname) {
        return calendarRepository.findByWriterId(nickname);
    }

    @Override
    public CalendarDTO updateSchedule(Long scheduleId, String nickName, LocalDate date, String schedule, boolean shared, String loverName) {
        CalendarDTO calendarDTO = calendarRepository.findById(scheduleId).orElse(null);

        if (calendarDTO == null) {
            return null;
        }
        calendarDTO.setWriterId(nickName);
        calendarDTO.setScheduleDate(date);
        calendarDTO.setScheduleContent(schedule);
        calendarDTO.setShared(shared);
        User lover = userService.findByNickName(loverName);
        calendarDTO.setLover(lover);
        return calendarRepository.save(calendarDTO);
    }

}