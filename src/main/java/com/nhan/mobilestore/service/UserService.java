package com.nhan.mobilestore.service;

import com.nhan.mobilestore.dto.RegisterDTO;
import com.nhan.mobilestore.entity.Role;
import com.nhan.mobilestore.entity.User;
import com.nhan.mobilestore.exception.AlreadyExistsException;
import com.nhan.mobilestore.exception.NotFoundException;
import com.nhan.mobilestore.mapper.UserMapper;
import com.nhan.mobilestore.repository.RoleRepository;
import com.nhan.mobilestore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    public RegisterDTO saveUser(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new AlreadyExistsException("User", "username", registerDTO.getUsername());
        }

        Role role = roleRepository.findById(registerDTO.getRole()).orElseThrow(()
                -> new NotFoundException("Role not found with id: " +  registerDTO.getRole().toString()));

        // Convert UserDTO to User
        User user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);

        return userMapper.toDTO(userRepository.save(user));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id.toString()));
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
