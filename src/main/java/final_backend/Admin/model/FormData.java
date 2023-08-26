package final_backend.Admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class FormData {
    private String reason;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate; // 블랙된 시작일
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate; // 블랙된 종료일 (null일 수 있음. 예: 영구적으로 블랙)
}
