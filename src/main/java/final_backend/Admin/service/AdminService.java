package final_backend.Admin.service;

import final_backend.Admin.model.UserBlackListDTO;
import final_backend.Member.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {
    UserBlackListDTO blockUserByUid(Long uid, String reason, LocalDateTime startDate, LocalDateTime endDate);
    List<User> findByNickName(String nickName);
}
