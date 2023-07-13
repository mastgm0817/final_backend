package final_backend.Calendar.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "MySchedule")
public class MyScheduleDTO {
    @Id
    @GeneratedValue
    private Long MyScheduleId;
    private String writerId;
    private LocalDate MyScheduleDate;
    private String MyScheduleContent;
    private boolean shared;
}
