package com.eunhye.onus_crud_3.controllers;

import com.eunhye.onus_crud_3.dtos.ApiResponseDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailRequestDTO;
import com.eunhye.onus_crud_3.dtos.email.EmailVerifyDTO;
import com.eunhye.onus_crud_3.dtos.login.AuthRequestDTO;
import com.eunhye.onus_crud_3.dtos.login.AuthResponseDTO;
import com.eunhye.onus_crud_3.dtos.user.UserRequestDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;
import com.eunhye.onus_crud_3.entities.User;
import com.eunhye.onus_crud_3.services.AuthService;
import com.eunhye.onus_crud_3.services.EmailService;
import com.eunhye.onus_crud_3.services.JwtService;
import com.eunhye.onus_crud_3.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {
    private UserService userService;
    private EmailService emailService;
    private AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO savedUser = userService.createUser(userRequestDTO);

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
        boolean isValid = authService.verifyEmailCode(emailVerifyDTO);

        if (!isValid) {
            ApiResponseDTO<Boolean> response = ApiResponseDTO.<Boolean>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("인증 코드가 유효하지 않습니다.")
                    .data(false)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ApiResponseDTO<Boolean> response = ApiResponseDTO.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("인증 코드 확인 성공")
                .data(true)
                .build();

        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> signIn(@RequestBody AuthRequestDTO authRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            User user = userService.findByEmail(authRequestDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            String accessToken = jwtService.generateToken(user.getEmail());
            String refreshToken = jwtService.generateToken(user.getEmail());

            AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            ApiResponseDTO<AuthResponseDTO> response = ApiResponseDTO.<AuthResponseDTO>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("로그인 성공")
                    .data(authResponseDTO)
                    .build();

            return ResponseEntity.ok(response);

        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
