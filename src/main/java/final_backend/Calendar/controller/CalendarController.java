package final_backend.Calendar.controller;


import final_backend.Calendar.model.ScheduleDTO;
import final_backend.Calendar.model.ScheduleRequestDTO;
import final_backend.Calendar.service.ScheduleService;
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
@CrossOrigin
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    @PostMapping("/setSchedule/nickName={nickName}")
    public ResponseEntity<String> createSchedule(
            @PathVariable("nickName") String nickName,
            @RequestBody ScheduleRequestDTO requestDTO) {
        User user = userService.findByNickName(nickName);
        System.out.println(user);
        String loverName = user.getLover();
        ScheduleDTO scheduleDTO;
        System.out.println(user);
        if (requestDTO.isShare()){
            scheduleDTO = scheduleService.CreateMySchedule(nickName, requestDTO.getDate(), requestDTO.getSchedule(), requestDTO.isShare(), loverName);
        }
        else {
            scheduleDTO = scheduleService.CreateMySchedule(nickName, requestDTO.getDate(), requestDTO.getSchedule(), requestDTO.isShare(), "");
        }
        System.out.println(scheduleDTO);
        return ResponseEntity.ok("user");
    }

    @GetMapping("/getSchedule/userName={Nickname}")
    public ResponseEntity<List<ScheduleDTO>> getAllScheduleByName(@PathVariable("Nickname") String Nickname) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByNickName(Nickname);
        System.out.println(schedules);
        return ResponseEntity.ok(schedules);

    }

//    @PostMapping("/updateSchedule/userName={userName}/scheduleId={scheduleId}")
//    public ResponseEntity<String> updateSchedule(@PathVariable("userName") String userName,
//                                                 @PathVariable("scheduleId") Long scheduleId,
//                                                 @RequestBody ScheduleRequestDTO RequestDTO) {
//
//    }

//    @GetMapping("/updateSchedule/userName={userName}")
//    public ResponseEntity<Map<String, Object>> updateform(@PathVariable("userName") String userName,
//                                                          @RequestParam("scheduleId") Long scheduleId,
//                                                          @RequestParam("shared") boolean shared) {
//        // userId를 통해 사용자 정보 조회
//    }
//
//    @PostMapping("/deleteSchedule/userName={userName}/scheduleId={scheduleId}")
//    public ResponseEntity<String> deleteSchedule(@PathVariable("userName") String userName,
//                                                 @PathVariable("scheduleId") Long scheduleId,
//                                                 @RequestParam("shared") boolean shared) {
//        User user = userService.findByUserName(userName);
//
//
//    }
}