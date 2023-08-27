package final_backend.Calendar.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import final_backend.Member.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "MySchedule")
public class CalendarDTO {
    @Id
    @GeneratedValue
    private Long scheduleId;
    private String writerId;
    private LocalDate scheduleDate;
    private String scheduleContent;
    private boolean shared;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User lover;
}