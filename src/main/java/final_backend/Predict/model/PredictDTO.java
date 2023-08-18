package final_backend.Predict.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictDTO {
    private String user_latitude;
    private String user_longitude;
    private String food;
    private int service;
    private int ambiance;
    private int storeCondition;
    private int quantity;
    private int taste;
    private int kindness;
}