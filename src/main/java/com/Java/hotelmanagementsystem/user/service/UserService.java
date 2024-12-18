package com.Java.hotelmanagementsystem.user.service;


import com.Java.hotelmanagementsystem.auth.helper.UserInfoDetails;
import com.Java.hotelmanagementsystem.user.mapper.UserMapper;
import com.Java.hotelmanagementsystem.user.model.UserDTO;
import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.repository.UserRepository;
import com.Java.hotelmanagementsystem.util.exception.GlobalExceptionWrapper;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.Java.hotelmanagementsystem.util.constants.UserConstants.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> user = this.userRepository.findAll();
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }


    @Override
    public UserDTO fetchById(long id) {
        User user = findById(id);
        return UserMapper.toDTO(user);
    }

    private User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
    }

    @Override
    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        User selectedUser = findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
        return UserMapper.toDTO(selectedUser);
    }

    public Optional<User> findByEmail(@NonNull String emailId) {
        return this.userRepository.findByEmail(emailId);
    }

    @Override
    public String update(long id, @NonNull UserDTO userDTO) {
        UserDTO authenticatedUser = fetchSelfInfo();

        //Allow update by admin to the user info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = UserMapper.toDTO(findById(id));
        }

        if (StringUtils.isNotBlank(userDTO.getCid())) {
            authenticatedUser.setCid(userDTO.getCid());
        }

        if (StringUtils.isNotBlank(userDTO.getName())) {
            authenticatedUser.setName(userDTO.getName());
        }

        if (StringUtils.isNotBlank(userDTO.getPhone())) {
            authenticatedUser.setPhone(userDTO.getPhone());
        }

        this.userRepository.save(UserMapper.toEntity(authenticatedUser));
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UserDTO authenticatedUser = fetchSelfInfo();

        //Allow to delete by admin to the user info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = UserMapper.toDTO(findById(id));

        }

        this.userRepository.deleteById(authenticatedUser.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }
}
