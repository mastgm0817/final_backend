// final_backend.Inquiry.model.Inquiry.java

package final_backend.Inquiry.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import final_backend.Member.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class InquiryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;  // 문의 내용
    private String completed; // 완료 여부
    private LocalDate createdAt; // 문의 시간

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @JsonManagedReference
    @OneToOne(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private ResponseDTO responseDTO;
}
