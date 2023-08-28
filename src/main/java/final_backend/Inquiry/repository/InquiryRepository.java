// final_backend.Inquiry.repository.InquiryRepository.java

package final_backend.Inquiry.repository;

import final_backend.Inquiry.model.InquiryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryDTO, Long> {
    // 필요한 커스텀 쿼리는 여기에 추가...
}
