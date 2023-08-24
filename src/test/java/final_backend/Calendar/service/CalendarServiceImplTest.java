// 이 코드는 오류가 납니당

//package final_backend.Calendar.service;
//
//import final_backend.Calendar.model.CalendarDTO;
//import final_backend.Calendar.repository.CalendarRepository;
//import final_backend.Member.model.User;
//import final_backend.Member.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CalendarServiceImplTest {
//
//    @Mock
//    private CalendarRepository calendarRepository;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private CalendarServiceImpl calendarService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateMySchedule() {
//        // Mock data
//        String userName = "user123";
//        LocalDate date = LocalDate.now();
//        String schedule = "Test schedule";
//        boolean share = true;
//        String loverName = "lover123";
//
//        User lover = new User();
//        lover.setNickName(loverName);
//
//        CalendarDTO calendarDTO = new CalendarDTO();
//        calendarDTO.setWriterId(userName);
//        calendarDTO.setScheduleDate(date);
//        calendarDTO.setScheduleContent(schedule);
//        calendarDTO.setShared(share);
//        calendarDTO.setLover(lover);
//
//        when(userService.findByNickName(loverName)).thenReturn(lover);
//        when(calendarRepository.save(any(CalendarDTO.class))).thenReturn(calendarDTO);
//
//        // Test the method
//        CalendarDTO createdSchedule = calendarService.CreateMySchedule(userName, date, schedule, share, loverName);
//        assertNotNull(createdSchedule);
//        assertEquals(userName, createdSchedule.getWriterId());
//        assertEquals(date, createdSchedule.getScheduleDate());
//        assertEquals(schedule, createdSchedule.getScheduleContent());
//        assertEquals(share, createdSchedule.isShared());
//        assertEquals(lover, createdSchedule.getLover());
//
//        verify(userService, times(1)).findByNickName(loverName);
//        verify(calendarRepository, times(1)).save(any(CalendarDTO.class));
//    }
//
//    @Test
//    void testGetSharedSchedulesByLoverName() {
//        // Mock data
//        String loverName = "lover123";
//        User lover = new User();
//        lover.setNickName(loverName);
//
//        CalendarDTO schedule1 = new CalendarDTO();
//        schedule1.setLover(lover);
//        schedule1.setShared(true);
//
//        CalendarDTO schedule2 = new CalendarDTO();
//        schedule2.setLover(lover);
//        schedule2.setShared(true);
//
//        List<CalendarDTO> sharedSchedules = new ArrayList<>();
//        sharedSchedules.add(schedule1);
//        sharedSchedules.add(schedule2);
//
//        when(calendarRepository.findByLoverAndSharedTrue(lover)).thenReturn(sharedSchedules);
//
//        // Test the method
//        List<CalendarDTO> retrievedSchedules = calendarService.getSharedSchedulesByLoverName(lover);
//        assertNotNull(retrievedSchedules);
//        assertEquals(2, retrievedSchedules.size());
//
//        verify(calendarRepository, times(1)).findByLoverAndSharedTrue(lover);
//    }
//
//    @Test
//    void testDeleteSchedule() {
//        // Mock data
//        Long scheduleId = 1L;
//        String nickName = "user123";
//        boolean shared = true;
//
//        CalendarDTO calendarDTO = new CalendarDTO();
//        calendarDTO.setWriterId(nickName);
//        calendarDTO.setShared(shared);
//
//        when(calendarRepository.findById(scheduleId)).thenReturn(Optional.of(calendarDTO));
//
//        // Test the method - Delete owned schedule
//        boolean deleteResult = calendarService.deleteSchedule(scheduleId, nickName, shared);
//        assertTrue(deleteResult);
//
//        // Test the method - Delete shared schedule
//        deleteResult = calendarService.deleteSchedule(scheduleId, "otherUser", true);
//        assertTrue(deleteResult);
//
//        // Test the method - Unauthorized deletion
//        deleteResult = calendarService.deleteSchedule(scheduleId, "otherUser", false);
//        assertFalse(deleteResult);
//
//        verify(calendarRepository, times(3)).findById(scheduleId);
//        verify(calendarRepository, times(1)).deleteById(scheduleId);
//    }
//
//    @Test
//    void testGetScheduleByNickName() {
//        // Mock data
//        String nickName = "user123";
//        CalendarDTO schedule1 = new CalendarDTO();
//        schedule1.setWriterId(nickName);
//
//        CalendarDTO schedule2 = new CalendarDTO();
//        schedule2.setWriterId(nickName);
//
//        List<CalendarDTO> userSchedules = new ArrayList<>();
//        userSchedules.add(schedule1);
//        userSchedules.add(schedule2);
//
//        when(calendarRepository.findByWriterId(nickName)).thenReturn(userSchedules);
//
//        // Test the method
//        List<CalendarDTO> retrievedSchedules = calendarService.getScheduleByNickName(nickName);
//        assertNotNull(retrievedSchedules);
//        assertEquals(2, retrievedSchedules.size());
//
//        verify(calendarRepository, times(1)).findByWriterId(nickName);
//    }
//
//    @Test
//    void testUpdateSchedule() {
//        // Mock data
//        Long scheduleId = 1L;
//        String nickName = "user123";
//        LocalDate date = LocalDate.now();
//        String scheduleContent = "Updated schedule";
//        boolean shared = true;
//        String loverName = "lover123";
//
//        User lover = new User();
//        lover.setNickName(loverName);
//
//        CalendarDTO existingSchedule = new CalendarDTO();
//        existingSchedule.setWriterId(nickName);
//        existingSchedule.setShared(shared);
//
//        when(calendarRepository.findById(scheduleId)).thenReturn(Optional.of(existingSchedule));
//        when(userService.findByNickName(loverName)).thenReturn(lover);
//
//        // Test the method
//        CalendarDTO updatedSchedule = calendarService.updateSchedule(scheduleId, nickName, date, scheduleContent, shared, loverName);
//        assertNotNull(updatedSchedule);
//        assertEquals(nickName, updatedSchedule.getWriterId());
//        assertEquals(date, updatedSchedule.getScheduleDate());
//        assertEquals(scheduleContent, updatedSchedule.getScheduleContent());
//        assertEquals(shared, updatedSchedule.isShared());
//        assertEquals(lover, updatedSchedule.getLover());
//
//        verify(calendarRepository, times(1)).findById(scheduleId);
//        verify(userService, times(1)).findByNickName(loverName);
//        verify(calendarRepository, times(1)).save(existingSchedule);
//    }
//
//
//}
