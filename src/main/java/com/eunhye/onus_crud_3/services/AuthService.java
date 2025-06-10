package com.eunhye.onus_crud_3.services;

import com.eunhye.onus_crud_3.dtos.email.EmailRequestDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailVerifyDTO;

public interface AuthService {
    String sendVerificationCode(EmailRequestDTO emailRequestDTO);
    boolean verifyEmailCode(EmailVerifyDTO emailVerifyDTO);
}
