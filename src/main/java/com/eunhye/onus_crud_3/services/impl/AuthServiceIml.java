package com.eunhye.onus_crud_3.services.impl;

import com.eunhye.onus_crud_3.dtos.email.EmailRequestDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailVerifyDTO;
import com.eunhye.onus_crud_3.repositories.UserRepository;
import com.eunhye.onus_crud_3.services.AuthService;
import com.eunhye.onus_crud_3.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceIml implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public String sendVerificationCode(EmailRequestDTO emailRequestDTO) {
        // 6자리 랜덤 코드 생성
        String code = String.format("%06d", new Random().nextInt(1000000));
        emailService.se
        return null;
    }

    @Override
    public boolean verifyEmailCode(EmailVerifyDTO emailVerifyDTO) {
        return false;
    }
}
