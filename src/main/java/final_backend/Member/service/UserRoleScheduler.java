package final_backend.Member.service;

import final_backend.Member.model.User;
import final_backend.Member.model.UserRole;
import final_backend.Member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserRoleScheduler {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void updateUserRoles() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getVipEndTime().isBefore(LocalDateTime.now())) {
                user.setUserRole(UserRole.Normal);
                userRepository.save(user);
            }
        }
    }
}
