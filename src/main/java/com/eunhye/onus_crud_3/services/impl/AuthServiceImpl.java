package com.eunhye.onus_crud_3.services.impl;

import com.eunhye.onus_crud_3.dtos.email.EmailRequestDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailVerifyDTO;
import com.eunhye.onus_crud_3.repositories.UserRepository;
import com.eunhye.onus_crud_3.services.AuthService;
import com.eunhye.onus_crud_3.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String sendVerificationCode(EmailRequestDTO emailRequestDTO) {
        // 6자리 랜덤 코드 생성
        String code = String.format("%06d", new Random().nextInt(1000000));
        emailService.sendVerificationCodeEmail(emailRequestDTO.getEmail(), code);

        // Redis에 저장 (유효시간 5분)
        redisTemplate.opsForValue().set("email:verify" + emailRequestDTO.getEmail(), code, 5, TimeUnit.MINUTES);
        String stored = redisTemplate.opsForValue().get("email:verify" + emailRequestDTO.getEmail());
        log.info("Redis에서 방금 저장된 값: {}", stored);

        return code;
    }

    @Override
    public boolean verifyEmailCode(EmailVerifyDTO emailVerifyDTO) {
        String redisKey = "email:verify" + emailVerifyDTO.getEmail();
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode == null) {
            throw new IllegalArgumentException("인증 코드가 존재하지 않거나 만료되었습니다.");
        }

        if (!storedCode.equals(emailVerifyDTO.getCode())) {
            throw new IllegalArgumentException("인증 코드가 일치하지 않습니다.");
        }

        redisTemplate.delete(redisKey); // 인증 코드 사용 후 삭제

        return true;
    }
}
