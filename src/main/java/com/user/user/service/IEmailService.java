package com.user.user.service;

import com.user.user.dto.ResponseDto;
import com.user.user.model.EmailModel;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    public ResponseEntity<ResponseDto> sendEmail(EmailModel emailModel);
}
