package final_backend.Calendar.service;

import final_backend.Calendar.model.CalendarDTO;
import final_backend.Member.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {
    CalendarDTO CreateMySchedule(String userName, LocalDate date, String schedule, boolean share, String loverName);

    List<CalendarDTO> getSharedSchedulesByLoverName(User loverName);

    CalendarDTO findById(Long scheduleId);

    // 삭제 기능
    boolean deleteSchedule(Long scheduleId, String nickName, boolean shared);

    List<CalendarDTO> getScheduleByNickName(String nickname);

    CalendarDTO updateSchedule(Long scheduleId, String nickName, LocalDate date, String schedule, boolean shared, String loverName);
}
