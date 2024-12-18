package com.Java.hotelmanagementsystem.user.controller;
import com.Java.hotelmanagementsystem.user.model.UserDTO;
import com.Java.hotelmanagementsystem.util.RestHelper;
import com.Java.hotelmanagementsystem.util.RestResponse;
import com.Java.hotelmanagementsystem.util.constants.UserConstants;
import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Fetch self info of the instructor
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> fetchSelf() {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the instructor by identifier.
     *
     * @param id The unique identifier of the instructor.
     * @return The instructor entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Signing up the new instructor.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userService.deleteById(id);
        return RestHelper.responseMessage(message);
    }

    /**
     * Fetches all the instructor entities in the system.
     *
     * @return The list of instructor entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("users", userService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing user entity.
     *
     * @param userDTO The updated user dto.
     * @return The message indicating the confirmation on updated user entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id,
                                               @Validated @RequestBody UserDTO userDTO) {
        String message = userService.update(id, userDTO);
        return RestHelper.responseMessage(message);
    }
}
