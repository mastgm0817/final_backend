package final_backend.Admin.controller;

import final_backend.Admin.model.BlackListRequest;
import final_backend.Admin.model.UserBlackListDTO;
import final_backend.Admin.service.AdminServiceImpl;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {
    private String secretKey;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminServiceImpl adminServiceImpl;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{nickName}")
    public ResponseEntity<List<User>> getUserInfoByNickName(@PathVariable String nickName) {
        List<User> user = adminServiceImpl.findByNickName(nickName); // 닉네임으로 유저 정보 검색
        if (user != null) {
            return ResponseEntity.ok(user); // 검색된 유저 정보 반환
        } else {
            return ResponseEntity.notFound().build(); // 유저 정보가 없으면 404 반환
        }
    }

    @PostMapping("/users/block/{uid}")
    public ResponseEntity<UserBlackListDTO> blockUser(@PathVariable Long uid, @RequestBody BlackListRequest blackListRequest) {

        try {
            UserBlackListDTO blockUser = adminServiceImpl.blockUserByUid(uid, blackListRequest.getReason(), blackListRequest.getStartDate(), blackListRequest.getEndDate());
            return ResponseEntity.ok(blockUser); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

//    @GetMapping("/boards")
//    public ResponseEntity<List<Board>> getAllBoard (){
//        return
//    }
}
