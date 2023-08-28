// final_backend.Inquiry.model.Inquiry.java

package final_backend.Inquiry.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import final_backend.Member.model.User;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class InquiryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;  // 문의 내용
    private boolean completed; // 완료 여부
    private LocalDate createdAt; // 문의 시간
    private LocalDate completedAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

//    @JsonManagedReference
//    @OneToOne(mappedBy = "inquiry", cascade = CascadeType.ALL)
//    private ResponseDTO responseDTO;

    @Override
    public String toString() {
        return "InquiryDTO{" +
                "id=" + id +
                ", title=" + title +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
