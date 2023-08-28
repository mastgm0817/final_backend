package final_backend.Inquiry.service;

import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.repository.InquiryRepository;
import final_backend.Member.model.User;
import org.springframework.stereotype.Service;

@Service
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public InquiryDTO createInquiry(InquiryDTO inquiryDTO, User user) {
        InquiryDTO inquiry = new InquiryDTO();

        inquiry.setCreatedAt(inquiryDTO.getCreatedAt());
        inquiry.setId(inquiryDTO.getId());
        inquiry.setContent(inquiryDTO.getContent());
        inquiry.setUser(user);
        inquiry.setCompleted(false);
        inquiry.setCompletedAt(null);
        inquiryRepository.save(inquiry);
        return inquiry;
    }
}
