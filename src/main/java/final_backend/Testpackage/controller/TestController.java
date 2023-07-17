package final_backend.Testpackage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String showTest(){
        return "test";
    }

//
//    @GetMapping("/")
//    public String showIndex(){
//        return "index";
//    }
}
