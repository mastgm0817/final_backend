package final_backend.Payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPaymentDTO {
    private String kpaynickName;
    private String kpayitemName;
    private String kpaycouponCode;
    // getters, setters
}
