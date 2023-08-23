package final_backend.Predict.controller;

import final_backend.Predict.model.PredictDTO;
import final_backend.Predict.model.RandomDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class RandomController {

    @PostMapping("/random")
    public String predict(@RequestBody RandomDTO randomDTO) {
        // Flask 서버의 URL 설정
        String flaskServerUrl = "http://127.0.0.1:5000/api/v1/random"; // 실제 Flask 서버의 URL로 변경해주세요
        System.out.println(randomDTO.getSelected_region());

        // Flask 서버로 데이터 전송
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Spring Connected And Try Flask Connection");
        ResponseEntity<String> response = restTemplate.postForEntity(flaskServerUrl, randomDTO, String.class);
        System.out.println("wow");
        String randomResult = response.getBody();

        return randomResult;
    }
}
