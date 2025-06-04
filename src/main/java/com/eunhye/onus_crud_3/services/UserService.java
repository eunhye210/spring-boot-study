package com.eunhye.onus_crud_3.services;

import com.eunhye.onus_crud_3.dtos.UserDTO;
import com.eunhye.onus_crud_3.dtos.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
}
