package com.Java.hotelmanagementsystem.user.controller;
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
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.fetchSelfInfo());
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
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userService.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
