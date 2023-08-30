package final_backend.Inquiry.service;

import final_backend.Inquiry.model.ResponseDTO;

public interface ResponseService {

    ResponseDTO saveResponse(Long InquiryId, String responseDTO);
}
