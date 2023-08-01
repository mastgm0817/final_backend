package final_backend.Calendar.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class CalendarRequestDTO {
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate date;
    private boolean share;
    private String schedule;

}