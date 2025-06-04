package com.eunhye.onus_crud_3.services.impl;

import com.eunhye.onus_crud_3.dtos.user.UserDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;
import com.eunhye.onus_crud_3.entities.User;
import com.eunhye.onus_crud_3.mapper.UserMapper;
import com.eunhye.onus_crud_3.repositories.UserRepository;
import com.eunhye.onus_crud_3.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = UserMapper.mapToUser(userDTO);
        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserResponseDTO(savedUser);
    }
}
