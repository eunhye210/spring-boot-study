package com.eunhye.onus_crud_3.services;

import com.eunhye.onus_crud_3.dtos.user.UserRequestDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;
import com.eunhye.onus_crud_3.entities.User;

import java.util.Optional;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    Optional<User> findByEmail(String email);
}
