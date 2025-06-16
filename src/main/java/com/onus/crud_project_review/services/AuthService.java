package com.onus.crud_project_review.services;

import com.onus.crud_project_review.dtos.EmailDTO;
import com.onus.crud_project_review.dtos.EmailVerifyRequestDTO;

public interface AuthService {
    String sendVerificationCode(EmailDTO emailDTO);
    boolean verifyEmailCode(EmailVerifyRequestDTO emailVerifyRequestDTO);
}
