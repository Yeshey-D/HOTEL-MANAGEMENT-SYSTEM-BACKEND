package com.Java.hotelmanagementsystem.user.controller;

import com.Java.hotelmanagementsystem.constants.UserConstants;
import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) throws Exception {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();

    }

    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        return userService.save(user);
    }

}
