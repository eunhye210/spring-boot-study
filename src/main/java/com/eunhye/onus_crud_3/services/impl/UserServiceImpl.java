package com.eunhye.onus_crud_3.services.impl;

import com.eunhye.onus_crud_3.dtos.user.UserRequestDTO;
import com.eunhye.onus_crud_3.dtos.user.UserResponseDTO;
import com.eunhye.onus_crud_3.entities.User;
import com.eunhye.onus_crud_3.mapper.UserMapper;
import com.eunhye.onus_crud_3.repositories.UserRepository;
import com.eunhye.onus_crud_3.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.mapToUser(userRequestDTO);
        User savedUser = userRepository.save(user);

        return userMapper.mapToUserResponseDTO(savedUser);
    }
}
