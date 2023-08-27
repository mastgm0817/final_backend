package final_backend.Predict.controller;

import final_backend.Predict.model.PredictDTO;
import final_backend.Predict.model.RandomDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class RandomController {

//    @Value("${flask.secret}")
//    private String flaskServerUrl;

    private String flaskServerUrl = "https://luvoost.co.kr:32000/api/v2/random";
//    private String flaskServerUrl = "https://localhost:32000/api/v2/random";
    @PostMapping("/random")
    public String predict(@RequestBody RandomDTO randomDTO) {
        // Flask 서버의 예측 엔드포인트 URL
        String predictEndpoint = flaskServerUrl;
        System.out.println(randomDTO.getSelected_region());

        // Flask 서버로 데이터 전송
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Spring Connected And Try Flask Connection");
        ResponseEntity<String> response = restTemplate.postForEntity(predictEndpoint, randomDTO, String.class);
        System.out.println("wow");
        String randomResult = response.getBody();

        return randomResult;
    }
}
