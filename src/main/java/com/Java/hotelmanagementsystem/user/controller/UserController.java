package com.Java.hotelmanagementsystem.user.controller;

import com.Java.hotelmanagementsystem.constants.UserConstants;
import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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


    @PutMapping("put/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Set the ID in the User object (to ensure the correct record is updated)
        user.setId(id);

        // Call the update method in the service layer
        String response = userService.update(user);

        if (UserConstants.UPDATE_SUCCESSFUL.equals(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(UserConstants.DELETE_SUCCESSFUL);
    }

}
