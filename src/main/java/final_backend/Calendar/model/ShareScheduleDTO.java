package final_backend.Calendar.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Share_Schedule")
public class ShareScheduleDTO {
    @Id
    @GeneratedValue
    private Long ShareScheduleId;
    private String shareScheduleWriterId;
    private String shareScheduleLoverId;
    private LocalDate shareScheduleDate;
    private String shareScheduleContent;
    private boolean shared;
}