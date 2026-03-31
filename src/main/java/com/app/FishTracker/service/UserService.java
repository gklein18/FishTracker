package com.app.FishTracker.service;

import com.app.FishTracker.dto.user.CreateUserRequest;
import com.app.FishTracker.dto.user.UpdateUserRequest;
import com.app.FishTracker.dto.user.UserDTO;
import com.app.FishTracker.model.User;
import com.app.FishTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO findByEmail(String email) {
        return toDTO(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDTO findByUserId(Long id) {
        return toDTO(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(toDTO(user));
        }

        return dtos;
    }

    public UserDTO createUser(CreateUserRequest request) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        return toDTO(userRepository.save(user));
    }

    public UserDTO updateUser(UpdateUserRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(request.getEmail());

        return toDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
