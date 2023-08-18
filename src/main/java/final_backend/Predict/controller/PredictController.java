package final_backend.Predict.controller;

import final_backend.Predict.model.PredictDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class PredictController {

    @PostMapping("/predict")
    public String predict(@RequestBody PredictDTO predictDTO) {
        // Flask 서버의 URL 설정
        String flaskServerUrl = "http://127.0.0.1:5000/api/v1/predict"; // 실제 Flask 서버의 URL로 변경해주세요
        System.out.println(predictDTO.getFood());
        System.out.println(predictDTO.getAmbiance());

        // Flask 서버로 데이터 전송
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Spring Connected And Try Flask Connection");
        ResponseEntity<String> response = restTemplate.postForEntity(flaskServerUrl, predictDTO, String.class);
        System.out.println("wow");
        String predictionResult = response.getBody();
        // 예측 결과를 JSON 형식으로 생성하여 반환합니다.
//        String predictionResult = "{\"latitude\":" + latitude + ", \"longitude\":" + longitude + "}";
        return predictionResult;
    }
}
