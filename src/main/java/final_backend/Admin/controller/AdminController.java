package final_backend.Admin.controller;

import final_backend.Admin.model.FormData;
import final_backend.Admin.service.AdminServiceImpl;
import final_backend.Member.model.User;
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
    public ResponseEntity<User> getUserInfoByNickName(@PathVariable String nickName) {
        User user = userService.findByNickName(nickName); // 닉네임으로 유저 정보 검색
        if (user != null) {
            return ResponseEntity.ok(user); // 검색된 유저 정보 반환
        } else {
            return ResponseEntity.notFound().build(); // 유저 정보가 없으면 404 반환
        }
    }

    @PostMapping("/users/block/{nickName}")
    public ResponseEntity<?> blockUser(@PathVariable String nickName, @RequestBody FormData formData) {
        System.out.println(formData.getStartDate());
        try {
            adminServiceImpl.blockUserByNickName(nickName, formData.getReason(), formData.getStartDate(), formData.getEndDate());
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

//    @GetMapping("/boards")
//    public ResponseEntity<List<Board>> getAllBoard (){
//        return
//    }
}
