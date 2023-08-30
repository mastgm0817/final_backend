package final_backend.Payment.service;

import final_backend.Payment.model.KakaoPaymentDTO;
import org.springframework.stereotype.Service;

@Service
public class SharedDTOService {
    private KakaoPaymentDTO kakaoPaymentDTO;

    public KakaoPaymentDTO getKakaoPaymentDTO() {
        return kakaoPaymentDTO;
    }

    public void setKakaoPaymentDTO(KakaoPaymentDTO kakaoPaymentDTO) {
        this.kakaoPaymentDTO = kakaoPaymentDTO;
    }
}
