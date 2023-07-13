package final_backend.Calendar.controller;


import final_backend.Calendar.model.MyScheduleDTO;
import final_backend.Calendar.model.ScheduleRequestDTO;
import final_backend.Calendar.model.ShareScheduleDTO;
import final_backend.Calendar.service.MyScheduleService;
import final_backend.Calendar.service.ShareScheduleService;
import final_backend.Member.model.User;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
//@CrossOrigin(origins = "http://192.168.0.13:3000")
public class CalendarController {
    @Autowired
    private MyScheduleService myScheduleService;
    @Autowired
    private UserService userService;

    @Autowired
    private ShareScheduleService shareScheduleService;

    @PostMapping("/setSchedule/userId={userId}")
    public ResponseEntity<String> createSchedule(
            @PathVariable("userId") String userId,
            @RequestBody ScheduleRequestDTO requestDTO) {

        User user = userService.findByUserId(userId);
        if (user != null) {
            if (requestDTO.isShare()) {
                ShareScheduleDTO savedShareSchedule = shareScheduleService.CreateShareSchedule(userId, requestDTO.getDate(), requestDTO.getSchedule(), requestDTO.isShare());
                return ResponseEntity.ok("Share Schedule Created!");
            } else {
                MyScheduleDTO savedMySchedule = myScheduleService.CreateMySchedule(userId, requestDTO.getDate(), requestDTO.getSchedule(), requestDTO.isShare());
                return ResponseEntity.ok("My Schedule Created!");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/getSchedule/userId={userId}")
    public ResponseEntity<List<Object>> getAllScheduleByName(@PathVariable("userId") String userId) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            List<MyScheduleDTO> mySchedules = myScheduleService.getScheduleByUserId(userId);
            List<ShareScheduleDTO> shareSchedules = shareScheduleService.getShareSchedule(userId);
            List<Object> combinedSchedules = new ArrayList<>();
            combinedSchedules.addAll(mySchedules);
            combinedSchedules.addAll(shareSchedules);

            if (!combinedSchedules.isEmpty()) {
                return ResponseEntity.ok(combinedSchedules);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/updateSchedule/userId={userId}/schedulId={scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable("userId") String userId,
                                                 @PathVariable("scheduleId") Long scheduleId,
                                                 @RequestBody ScheduleRequestDTO RequestDTO) {
        User user = userService.findByUserId(userId);

        if (user != null) {
            if (RequestDTO.isShare()) {
                ShareScheduleDTO updatedShareSchedule = shareScheduleService.findById(scheduleId);
                if ((updatedShareSchedule != null && updatedShareSchedule.getShareScheduleWriterId().equals(userId)) || updatedShareSchedule.getShareScheduleLoverId().equals(userId)) {
                    shareScheduleService.updateShareSchedule(userId, scheduleId, RequestDTO.getDate(), RequestDTO.getSchedule(), RequestDTO.isShare());
                }
                return ResponseEntity.ok("Share Schedule Updated!");
            } else {
                MyScheduleDTO updatedMySchedule = myScheduleService.findById(scheduleId);
                return ResponseEntity.ok("My Schedule Updated!");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/updateSchedule/userId={userId}")
    public ResponseEntity<Map<String, Object>> updateform(@PathVariable("userId") String userId,
                                                          @RequestParam("scheduleId") Long scheduleId,
                                                          @RequestParam("shared") boolean shared) {
        // userId를 통해 사용자 정보 조회

        Map<String, Object> response = new HashMap<>();

        if (shared) {
            ShareScheduleDTO shareSchedule = shareScheduleService.findById(scheduleId);
            if ((shareSchedule != null && shareSchedule.getShareScheduleWriterId().equals(userId)) || shareSchedule.getShareScheduleLoverId().equals(userId)) {
                // 공유용 일정을 작성한 사용자와 요청한 사용자가 일치하는지 확인
                // 일정 수정 로직 구현

                response.put("sharedSchedule", shareSchedule);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "User not authorized or schedule not found");
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            MyScheduleDTO mySchedule = myScheduleService.findById(scheduleId);
            if (mySchedule != null && mySchedule.getWriterId().equals(userId)) {
                // 개인용 일정을 작성한 사용자와 요청한 사용자가 일치하는지 확인
                // 일정 수정 로직 구현

                response.put("mySchedule", mySchedule);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "User not authorized or schedule not found");
                return ResponseEntity.badRequest().body(response);
            }
        }
    }

    @PostMapping("/deleteSchedule/userId={userId}/scheduleId={scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("userId") String userId,
                                                 @PathVariable("scheduleId") Long scheduleId,
                                                 @RequestParam("shared") boolean shared) {
        User user = userService.findByUserId(userId);

        if (user != null) {
            if (shared) {
                ShareScheduleDTO shareSchedule = shareScheduleService.findById(scheduleId);
                if ((shareSchedule != null && shareSchedule.getShareScheduleWriterId().equals(userId)) || shareSchedule.getShareScheduleLoverId().equals(userId)) {
                    shareScheduleService.deleteShareSchedule(userId, scheduleId);
                    return ResponseEntity.ok("Share Schedule Deleted!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not authorized or schedule not found");
                }
            } else {
                MyScheduleDTO mySchedule = myScheduleService.findById(scheduleId);
                if (mySchedule != null && mySchedule.getWriterId().equals(userId)) {
                    myScheduleService.deleteMySchedule(scheduleId);
                    return ResponseEntity.ok("My Schedule Deleted!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not authorized or schedule not found");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}