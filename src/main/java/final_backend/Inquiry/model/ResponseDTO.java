package final_backend.Inquiry.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "inquiry_id")
    private InquiryDTO inquiry;

    private String comment; // 답변 내용
    private LocalDate completedAt; // 답변 작성 시간

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "id=" + id +
                ", comment=" + comment +
                ", completedAt=" + completedAt +
                '}';
    }
}