package final_backend.Inquiry.service;

import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.model.InquiryStatus;
import final_backend.Inquiry.model.ResponseDTO;
import final_backend.Inquiry.repository.InquiryRepository;
import final_backend.Inquiry.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ResponseServiceImpl implements ResponseService{
    private final ResponseRepository responseRepository;
    private final InquiryService inquiryService;
    private final InquiryRepository inquiryRepository;

    public ResponseServiceImpl(ResponseRepository responseRepository, InquiryService inquiryService, InquiryRepository inquiryRepository) {
        this.responseRepository = responseRepository;
        this.inquiryService = inquiryService;
        this.inquiryRepository = inquiryRepository;
    }
    @Override
    public ResponseDTO saveResponse(Long InquiryId,String comment) {
        ResponseDTO responseDTO = new ResponseDTO();
        InquiryDTO inquiryDTO = inquiryService.getInquiryById(InquiryId);
        responseDTO.setInquiry(inquiryDTO);
        responseDTO.setComment(comment);
        responseDTO.setCompletedAt(LocalDate.now());

        responseRepository.save(responseDTO);
        inquiryDTO.setStatus(InquiryStatus.COMPLETED);
        inquiryDTO.setResponseDTO(responseDTO);
        inquiryRepository.save(inquiryDTO);
        return responseDTO;
    }
}
