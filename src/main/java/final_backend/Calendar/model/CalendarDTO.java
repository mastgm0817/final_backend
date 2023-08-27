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

    // CalendarDTO 클래스의 toString 메서드에서 lover를 출력하지 않도록 수정
    @Override
    public String toString() {
        return "CalendarDTO{" +
                "scheduleId=" + scheduleId +
                ", writerId='" + writerId + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", scheduleContent='" + scheduleContent + '\'' +
                ", shared=" + shared +
                '}';
    }
}

