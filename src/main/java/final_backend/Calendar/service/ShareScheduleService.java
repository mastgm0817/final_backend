package final_backend.Calendar.service;

import final_backend.Calendar.model.ShareScheduleDTO;
import final_backend.Calendar.repository.ShareScheduleRepository;
import final_backend.Member.model.User;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShareScheduleService {
    @Autowired
    private UserService userService;
    @Autowired
    private ShareScheduleRepository shareScheduleRepository;
    public ShareScheduleDTO CreateShareSchedule(String userName, LocalDate date, String schedule, boolean share) {
        User user = userService.findByUserName(userName);
        ShareScheduleDTO shareSchedule = new ShareScheduleDTO();
        shareSchedule.setShareScheduleWriterId(user.getName());
        shareSchedule.setShareScheduleLoverId(user.getLover());
        shareSchedule.setShareScheduleDate(date);
        shareSchedule.setShareScheduleContent(schedule);
        shareSchedule.setShared(share);
        return shareScheduleRepository.save(shareSchedule);
        }

    public List<ShareScheduleDTO> getShareSchedule(String userName) {
        return shareScheduleRepository.findByShareScheduleWriterIdOrShareScheduleLoverId(userName, userName);
    }

    public ShareScheduleDTO findById(Long scheduleId) {
        return shareScheduleRepository.findById(scheduleId).orElse(null);
    }

    public ShareScheduleDTO updateShareSchedule(String userName, Long scheduleId, LocalDate date, String schedule, boolean share) {
        User user = userService.findByUserName(userName);
        String lover = null;
        if (user.getLover()==userName) {
            lover = user.getName();
        }
        else {
            lover = user.getLover();
        }
        ShareScheduleDTO shareSchedule = findById(scheduleId);
        shareSchedule.setShareScheduleWriterId(userName);
        shareSchedule.setShareScheduleLoverId(lover);
        shareSchedule.setShareScheduleDate(date);
        shareSchedule.setShareScheduleContent(schedule);
        shareSchedule.setShared(share);
        return shareScheduleRepository.save(shareSchedule);
    }

    public void deleteShareSchedule(String userId, Long scheduleId) {
        shareScheduleRepository.deleteById(scheduleId);
    }

//    public ShareScheduleDTO updateShareSchedule(Long scheduleId, ScheduleRequestDTO scheduleRequestDTO) {
//        ShareScheduleDTO shareSchedule = findById(scheduleId);
//
//    }

}
