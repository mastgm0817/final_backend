package final_backend.Inquiry.service;

import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Member.model.User;

public interface InquiryService {
    InquiryDTO createInquiry(InquiryDTO inquiryDTO, User user);
}
