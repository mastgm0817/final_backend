package final_backend.Predict.controller;

import final_backend.Predict.model.PredictDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class PredictController {


//    @Value("${flask.secret}")
//    private String flaskServerUrl;
//    private String flaskServerUrl1 = "https://luvoost.co.kr:32000/api/v2/predict";
    private String flaskServerUrl = "http://localhost:32000/api/v2/predict";

    @PostMapping("/predict")
    public String predict(@RequestBody PredictDTO predictDTO) {

        System.out.println(predictDTO.getFood());
        System.out.println(predictDTO.getAmbiance());

        // Flask 서버의 예측 엔드포인트 URL
        String predictEndpoint = flaskServerUrl;

        // Flask 서버로 데이터 전송
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Spring Connected And Try Flask Connection");

        ResponseEntity<String> response = restTemplate.postForEntity(predictEndpoint, predictDTO, String.class);


        System.out.println("wow");
        String predictionResult = response.getBody();
        // 예측 결과를 JSON 형식으로 생성하여 반환합니다.
//        String predictionResult = "{\"latitude\":" + latitude + ", \"longitude\":" + longitude + "}";
        return predictionResult;
    }
}
