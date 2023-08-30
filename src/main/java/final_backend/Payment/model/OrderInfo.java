package final_backend.Payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class OrderInfo {
    private String cid;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private int quantity;
    private int total_amount;
    private int vat_amount;
    private int tax_free_amount;
    private String approval_url;
    private String fail_url;
    private String cancel_url;
    private String nickName;
    private Map<String, Object> params;
    private Map<String, Object> couponInfo;

    // getters and setters
}