package final_backend.Admin.service;

import final_backend.Admin.model.UserBlackListDTO;
import final_backend.Admin.repository.UserBlackListRepository;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Transactional
    public UserBlackListDTO blockUserByUid(Long uid, String reason, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));
        UserBlackListDTO blackListDetails = new UserBlackListDTO();
        blackListDetails.setReason(reason);
        blackListDetails.setStartDate(startDate);
        blackListDetails.setEndDate(endDate);
        blackListDetails.setBlockUser(user);

        return userBlackListRepository.save(blackListDetails);
    }

    @Override
    @Transactional
    public List<User> findByNickName(String nickName) {
        List<User> users = userRepository.findAllByNickName(nickName);  // 먼저 List<User>를 가져옵니다.
        List<User> userDTOs = new ArrayList<>();  // 변환된 User들을 저장할 리스트를 생성합니다.

        for (User user : users) {  // users 리스트의 각 User에 대해서
            User userDTO = new User();  // 새 User 객체를 생성합니다.
            userDTO.setUid(user.getUid());
            userDTO.setNickName(user.getNickName());
            userDTO.setEmail(user.getEmail());
            userDTO.setUserRole(user.getUserRole());
            userDTO.setCouponList(user.getCouponList());
            userDTO.setBlackListDetails(user.getBlackListDetails());
            userDTO.setLover(user.getLover());
            userDTO.setInquiryList(user.getInquiryList());
            userDTOs.add(userDTO);  // 생성한 User 객체를 userDTOs 리스트에 추가합니다.

        }

        return userDTOs;  // 변환된 User 객체들의 리스트를 반환합니다.
    }

}
