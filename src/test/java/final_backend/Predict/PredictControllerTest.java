//package final_backend.Predict;
//
//
//import final_backend.Predict.controller.PredictController;
//import final_backend.Predict.model.PredictDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class PredictControllerTest {
//
//    @InjectMocks
//    private PredictController predictController;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    private PredictDTO predictDTO;
//    private String flaskServerUrl = "http://127.0.0.1:5000/api/v1/predict";
//    private String expectedResult;
//
//    @BeforeEach
//    public void setUp() {
//        predictDTO = new PredictDTO();
//        predictDTO.setUser_latitude("37.468463");
//        predictDTO.setUser_longitude("126.886330");
//        predictDTO.setFood("치킨");
//        predictDTO.setAmbiance(3);
//        predictDTO.setService(3);
//        predictDTO.setStoreCondition(2);
//        predictDTO.setQuantity(4);
//        predictDTO.setTaste(5);
//        predictDTO.setKindness(4);
//
//        expectedResult = "{\"result\":\"예측 결과 테스트\"}";
//    }
//
//    @Test
//    public void testPredict() {
//        // restTemplate 가 예측 결과를 반환하는 것을 가정
//        when(restTemplate.postForEntity(eq(flaskServerUrl), any(HttpEntity.class), eq(String.class)))
//                .thenReturn(new ResponseEntity<>(expectedResult, HttpStatus.OK));
//
//        // 예측 결과 얻기
//        String result = predictController.predict(predictDTO);
//
//        // 결과 검증
//        assertEquals(expectedResult, result);
//    }
//}