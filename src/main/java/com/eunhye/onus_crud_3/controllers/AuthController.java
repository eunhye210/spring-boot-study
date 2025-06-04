package com.eunhye.onus_crud_3.controllers;

import com.eunhye.onus_crud_3.dtos.ApiResponseDTO;
import com.eunhye.onus_crud_3.dtos.user.UserDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;
import com.eunhye.onus_crud_3.services.EmailService;
import com.eunhye.onus_crud_3.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {
    private UserService userService;
    private EmailService emailService;

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
}
