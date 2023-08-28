package final_backend.Inquiry.contorller;

import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.model.InquiryRequest;
import final_backend.Inquiry.service.InquiryService;
import final_backend.Member.model.User;
import final_backend.Member.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;
    private final UserService userService;
    public InquiryController(InquiryService inquiryService, UserService userService) {
        this.inquiryService = inquiryService;
        this.userService = userService;
    }
e
    @PostMapping("/{nickName}")
    public ResponseEntity<?> createInquiry(@PathVariable("nickName") String nickName, @RequestBody InquiryRequest inquiryDTO) {
        User user = userService.findByNickName(nickName);
        if (user!=null) {
            try {
                return ResponseEntity.ok(inquiryService.createInquiry(inquiryDTO, user));
            }
            catch (RuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<?> getMyInquiry(@PathVariable("nickName") String nickName) {
        User user = userService.findByNickName(nickName);
        if (user!=null) {
            try{
                return ResponseEntity.ok(inquiryService.findByUser(user));
            }
            catch (RuntimeException e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
