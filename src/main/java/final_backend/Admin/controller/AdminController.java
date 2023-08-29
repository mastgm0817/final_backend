package final_backend.Admin.controller;

import com.google.analytics.data.v1beta.Row;
import com.google.analytics.data.v1beta.RunReportResponse;
import final_backend.Admin.Ga;
import final_backend.Admin.model.BlackListRequest;
import final_backend.Admin.model.UserBlackListDTO;
import final_backend.Admin.service.AdminServiceImpl;
import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.model.ResponseDTO;
import final_backend.Inquiry.service.InquiryService;
import final_backend.Inquiry.service.ResponseService;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import final_backend.Member.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static final_backend.Admin.Ga.sampleRunReport;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {
    private String secretKey;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminServiceImpl adminServiceImpl;
    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private ResponseService responseService;
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
    @GetMapping("/GA/{dim}/{met}/{startDate}/{endDate}")
    public ResponseEntity<?> getAnalytics(@PathVariable("dim") String dim, @PathVariable("met") String met, @PathVariable("startDate") String startDate , @PathVariable("endDate") String endDate) throws Exception {
        String propertyId = "403087661";

        RunReportResponse reportResponse = Ga.sampleRunReport(propertyId, dim, met, startDate, endDate);

        List<Map<String, Object>> resultRows = new ArrayList<>();
        for (Row row : reportResponse.getRowsList()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("dimension_value", row.getDimensionValues(0).getValue());
            rowMap.put("metric_value", Long.parseLong(row.getMetricValues(0).getValue()));
            resultRows.add(rowMap);
        }
        return ResponseEntity.ok(resultRows);
    }

    @GetMapping("getAllInquiry")
    public ResponseEntity<?> getAllInquiry() {
        return ResponseEntity.ok(inquiryService.findAll());
    }
    @PostMapping("/inquiry/{inquiryId}/response")
    public ResponseEntity<ResponseDTO> createResponse(@PathVariable Long inquiryId, @RequestBody String comment) {
        // InquiryID를 사용하여 해당 문의를 가져온 후, ResponseDTO와 연결하여 저장
        ResponseDTO savedResponse = responseService.saveResponse(inquiryId,comment);

        return ResponseEntity.ok(savedResponse);
    }
}
