package final_backend.Admin.service;

import final_backend.Member.model.User;

import java.time.LocalDateTime;

public interface AdminService {
    void blockUserByNickName(String nickName, String reason, LocalDateTime startDate, LocalDateTime endDate);
}
