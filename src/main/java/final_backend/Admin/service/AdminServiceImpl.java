package final_backend.Admin.service;

import final_backend.Admin.model.UserBlackListDTO;
import final_backend.Admin.repository.UserBlackListRepository;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private String secretKey;
    private final UserRepository userRepository;
    private final UserBlackListRepository userBlackListRepository;
    public AdminServiceImpl(UserRepository userRepository, UserBlackListRepository userBlackListRepository) {
        this.userRepository = userRepository;
        this.userBlackListRepository = userBlackListRepository;
    }

    @Override
    public void blockUserByNickName(String nickName, String reason,LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findByNickName(nickName);
        UserBlackListDTO blackListDTO = new UserBlackListDTO();
        blackListDTO.setReason(reason);
        blackListDTO.setStartDate(startDate);
        blackListDTO.setEndDate(endDate);

        blackListDTO.setBlockUser(user);

        userBlackListRepository.save(blackListDTO);
        user.setBlocked(true);
        user.setBlackListDetails(blackListDTO);

        userRepository.save(user);
    }

}
