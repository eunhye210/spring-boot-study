package com.eunhye.onus_crud_3.services;

import com.eunhye.onus_crud_3.dtos.user.UserDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
}
