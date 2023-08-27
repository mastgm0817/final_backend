package final_backend.Calendar.service;

import final_backend.Calendar.model.CalendarDTO;
import final_backend.Calendar.repository.CalendarRepository;
import final_backend.Member.model.User;
import final_backend.Member.service.UserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;

    private final UserServiceImpl userServiceImpl;

    public CalendarServiceImpl(CalendarRepository calendarRepository, UserServiceImpl userServiceImpl) {
        this.calendarRepository = calendarRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    @Transactional
    public CalendarDTO CreateMySchedule(String userName, LocalDate date, String schedule, boolean share, String loverName) {
        CalendarDTO mySchedule = new CalendarDTO();
        mySchedule.setWriterId(userName);
        mySchedule.setScheduleDate(date);
        mySchedule.setScheduleContent(schedule);
        mySchedule.setShared(share);

        User lover = userServiceImpl.findByNickName(loverName);
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

        if (!calendarDTO.getWriterId().equals(nickName) && !(shared && calendarDTO.getLover() != null && calendarDTO.getLover().getNickName().equals(nickName))) {
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
        User lover = userServiceImpl.findByNickName(loverName);
        calendarDTO.setLover(lover);
        return calendarRepository.save(calendarDTO);
    }

}