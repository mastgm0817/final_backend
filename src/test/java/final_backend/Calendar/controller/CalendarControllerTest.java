//package final_backend.Calendar.controller;
//
//import final_backend.Calendar.model.CalendarDTO;
//import final_backend.Calendar.model.CalendarRequestDTO;
//import final_backend.Calendar.service.CalendarService;
//import final_backend.Member.model.User;
//import final_backend.Member.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class CalendarControllerTest {
//
//    @InjectMocks
//    CalendarController calendarController;
//
//    @Mock
//    CalendarService calendarService;
//
//    @Mock
//    UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateSchedule() {
//        String nickName = "John";
//        CalendarRequestDTO requestDTO = new CalendarRequestDTO();
//        requestDTO.setShare(true);
//        User mockUser = new User();
//        mockUser.setLover("Jane");
//        when(userService.findByNickName(nickName)).thenReturn(mockUser);
//
//        ResponseEntity<String> response = calendarController.createSchedule(nickName, requestDTO);
//
//        assertEquals(200, response.getStatusCodeValue());
//        verify(calendarService).CreateMySchedule(anyString(), any(), any(), anyBoolean(), anyString());
//    }
//
//    @Test
//    void testGetAllScheduleByName() {
//        String nickName = "John";
//        User mockUser = new User();
//        mockUser.setLover("Jane");
//        when(userService.findByNickName(nickName)).thenReturn(mockUser);
//        when(calendarService.getScheduleByNickName(nickName)).thenReturn(Arrays.asList(new CalendarDTO()));
//        when(calendarService.getSharedSchedulesByLoverName(mockUser)).thenReturn(Arrays.asList(new CalendarDTO()));
//
//        ResponseEntity<?> response = calendarController.getAllScheduleByName(nickName);
//
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    void testUpdateSchedule() {
//        String nickName = "John";
//        Long scheduleId = 1L;
//        CalendarRequestDTO requestDTO = new CalendarRequestDTO();
//        User mockUser = new User();
//        mockUser.setLover("Jane");
//        when(userService.findByNickName(nickName)).thenReturn(mockUser);
//        when(calendarService.updateSchedule(anyLong(), anyString(), any(), any(), anyBoolean(), anyString())).thenReturn(new CalendarDTO());
//
//        ResponseEntity<?> response = calendarController.updateSchedule(nickName, scheduleId, requestDTO);
//
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    void testDeleteSchedule() {
//        String nickName = "John";
//        Long scheduleId = 1L;
//        User mockUser = new User();
//        mockUser.setLover("Jane");
//        when(userService.findByNickName(nickName)).thenReturn(mockUser);
//        when(calendarService.deleteSchedule(anyLong(), anyString(), anyBoolean())).thenReturn(true);
//
//        ResponseEntity<?> response = calendarController.deleteSchedule(nickName, scheduleId, true);
//
//        assertEquals(200, response.getStatusCodeValue());
//    }
//}