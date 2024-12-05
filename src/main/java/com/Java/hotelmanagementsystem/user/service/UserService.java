package com.Java.hotelmanagementsystem.user.service;


import com.Java.hotelmanagementsystem.auth.helper.UserInfoDetails;
import com.Java.hotelmanagementsystem.user.mapper.UserMapper;
import com.Java.hotelmanagementsystem.user.model.UserDTO;
import com.Java.hotelmanagementsystem.util.constants.UserConstants;
import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.repository.UserRepository;
import com.Java.hotelmanagementsystem.util.exception.GlobalExceptionWrapper;
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
    public UserDTO save(@lombok.NonNull User user) {
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
    public UserDTO findById(long id) throws Exception {
        return null;
    }


    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    public Optional<UserDTO> findByEmail(@lombok.NonNull String emailId) {
        Optional<User> user = this.userRepository.findByEmail(emailId);
        return UserMapper.toDTO(user);
    }

    @Override
    public String update(long id, User entity) {
        return "";
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow to delete by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            userEntity = UserMapper.toEntity(findById(id));
        }

        this.userRepository.deleteById(userEntity.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }


}
