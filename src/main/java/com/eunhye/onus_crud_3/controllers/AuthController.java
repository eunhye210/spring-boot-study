package com.eunhye.onus_crud_3.controllers;

import com.eunhye.onus_crud_3.dtos.ApiResponseDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailRequestDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailVerifyDTO;
import com.eunhye.onus_crud_3.dtos.user.UserDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;
import com.eunhye.onus_crud_3.services.AuthService;
import com.eunhye.onus_crud_3.services.EmailService;
import com.eunhye.onus_crud_3.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {
    private UserService userService;
    private EmailService emailService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUser(
            @Valid @RequestBody UserDTO userDTO
    ) {
        UserResponseDTO savedUser = userService.createUser(userDTO);

        // 웰컴 메일 전송
        emailService.sendEmail(savedUser.getEmail(), savedUser.getUserName());

        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(savedUser)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<ApiResponseDTO<Boolean>> sendAuthenticationEmail(
            @RequestBody EmailRequestDTO emailRequestDTO
    ) {
        authService.sendVerificationCode(emailRequestDTO);

        ApiResponseDTO<Boolean> response = ApiResponseDTO.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("이메일 전송 성공")
                .data(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify/email")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkAuthenticationCode(
            @RequestBody EmailVerifyDTO emailVerifyDTO
    ) {
        authService.verifyEmailCode(emailVerifyDTO);

        ApiResponseDTO<Boolean> response = ApiResponseDTO.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("인증 코드 확인 성공")
                .data(true)
                .build();

        return ResponseEntity.ok(response);
    }
}
