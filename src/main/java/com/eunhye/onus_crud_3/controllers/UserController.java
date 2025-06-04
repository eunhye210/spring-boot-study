package com.eunhye.onus_crud_3.controllers;

import com.eunhye.onus_crud_3.dtos.UserDTO;
import com.eunhye.onus_crud_3.dtos.UserResponseDTO;
import com.eunhye.onus_crud_3.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }
}
