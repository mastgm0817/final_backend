package final_backend.Calendar.controller;

import final_backend.Calendar.model.CalendarDTO;
import final_backend.Calendar.model.CalendarRequestDTO;
import final_backend.Calendar.service.CalendarServiceImpl;
import final_backend.Member.model.User;
import final_backend.Member.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/calendar")
public class CalendarController {
    @Autowired
    private CalendarServiceImpl calendarServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/{nickName}")
    public ResponseEntity<String> createSchedule(
            @PathVariable("nickName") String nickName,
            @RequestBody CalendarRequestDTO requestDTO) {
        User user = userServiceImpl.findByNickName(nickName);
        if (user != null) {
            System.out.println(user);
            CalendarDTO calendarDTO;
            System.out.println(user);
            if (requestDTO.isShare()) {
                calendarDTO = calendarServiceImpl.CreateMySchedule(nickName, requestDTO.getDate(), requestDTO.getSchedule(), requestDTO.isShare(), user.getLover());
            } else {
                calendarDTO = calendarServiceImpl.CreateMySchedule(nickName, requestDTO.getDate(), requestDTO.getSchedule(), requestDTO.isShare(), "");
            }
            System.out.println(calendarDTO);
            return ResponseEntity.ok("user");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<List<CalendarDTO>> getAllScheduleByName(@PathVariable("nickName") String nickName) {
        User user = userServiceImpl.findByNickName(nickName);
        if (user != null) {
            String loverName = user.getLover();
            List<CalendarDTO> schedules = calendarServiceImpl.getScheduleByNickName(nickName);
            List<CalendarDTO> sharedSchedules = calendarServiceImpl.getSharedSchedulesByLoverName(user);
            List<CalendarDTO> allSchedules = new ArrayList<>();

            allSchedules.addAll(schedules);
            allSchedules.addAll(sharedSchedules);

            System.out.println(schedules);
            return ResponseEntity.ok(allSchedules);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{nickName}/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable("nickName") String nickName,
                                                 @PathVariable("scheduleId") Long scheduleId,
                                                 @RequestBody CalendarRequestDTO RequestDTO) {
        User user = userServiceImpl.findByNickName(nickName);

        if (user != null) {
            String loverName = user.getLover();
            CalendarDTO updatedSchedule = calendarServiceImpl.updateSchedule(scheduleId, nickName, RequestDTO.getDate(), RequestDTO.getSchedule(), RequestDTO.isShare(), loverName);

            if (updatedSchedule != null) {
                return ResponseEntity.ok("Schedule updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 삭제
    @DeleteMapping("/{nickName}/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("nickName") String nickName,
                                                 @PathVariable("scheduleId") Long scheduleId,
                                                 @RequestParam("shared") boolean shared) {
        User user = userServiceImpl.findByNickName(nickName);

        if (user != null) {
            boolean isDeleted = calendarServiceImpl.deleteSchedule(scheduleId, nickName, shared);
            if (isDeleted) {
                return ResponseEntity.ok("Schedule deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found or not eligible for deletion");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}