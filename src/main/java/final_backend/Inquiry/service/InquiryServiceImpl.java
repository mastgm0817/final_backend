package final_backend.Inquiry.service;

import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.model.InquiryRequest;
import final_backend.Inquiry.repository.InquiryRepository;
import final_backend.Member.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    @Transactional
    public InquiryDTO createInquiry(InquiryRequest inquiryDTO, User user) {
        InquiryDTO inquiry = new InquiryDTO();
        inquiry.setTitle(inquiryDTO.getTitle());
        inquiry.setCreatedAt(LocalDate.now());
        inquiry.setContent(inquiryDTO.getContent());
        inquiry.setUser(user);
        inquiry.setCompleted(false);
        inquiry.setCompletedAt(null);
        user.getInquiryList().add(inquiry);
        inquiryRepository.save(inquiry);
        return inquiry;
    }
    @Override
    public List<InquiryDTO> findByUser(User user) {
        return inquiryRepository.findByUser(user);
    }
}
