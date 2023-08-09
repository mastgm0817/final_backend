package final_backend.predict.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO {
    private int Ambiance;
    private String BusinessName;
    private String FullAddresss;
    private int Kindness;
    private int Quantity;
    private int Service;
    private int StoreCondition;
    private int Taste;
    private String Food;
    private String latitude;
    private String longitude;
}
