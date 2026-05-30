package com.app.FishTracker.controller;

import com.app.FishTracker.dto.user.CreateUserRequest;
import com.app.FishTracker.dto.user.UpdateUserRequest;
import com.app.FishTracker.dto.user.UserDTO;
import com.app.FishTracker.model.User;
import com.app.FishTracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/{id}")
    public UserDTO getUserByUserId(@PathVariable Long id) {
        return userService.findByUserId(id);
    }

    @PostMapping()
    public UserDTO createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/update")
    public UserDTO updateUser(@RequestBody UpdateUserRequest request) {
        return userService.updateUser(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
