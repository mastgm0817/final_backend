package final_backend.Inquiry.service;

import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.model.InquiryRequest;
import final_backend.Member.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InquiryService {
    InquiryDTO createInquiry(InquiryRequest inquiryDTO, User user);
    List<InquiryDTO> findByUser(User user);

    InquiryDTO getInquiryById(Long inquiryId);

    List<InquiryDTO> findAll();
}
