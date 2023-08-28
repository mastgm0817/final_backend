package final_backend.Inquiry.service;

import final_backend.Inquiry.repository.InquiryRepository;
import org.springframework.stereotype.Service;

@Service
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }
}
