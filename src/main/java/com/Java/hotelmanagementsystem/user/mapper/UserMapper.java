package com.Java.hotelmanagementsystem.user.mapper;

import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    /**
     * Maps the instructor to instructor dto.
     *
     * @param user The instructor entity.
     * @return Returns the instructor entity.
     */
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto, "password");
        return dto;
    }

    /**
     * Maps the list of instructors to instructor dto
     *
     * @param users The list of instructor entity
     * @return The list of instructor dto.
     */
    public static List<UserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional instructor to optional instructor dto.
     *
     * @param user The instructor entity
     * @return The optional instructor dto.
     */
    public static Optional<UserDTO> toDTO(Optional<User> user) {
        return user.map(UserMapper::toDTO);
    }

    /**
     * Maps the instructor dto  to the instructor entity.
     *
     * @param dto The user dto.
     * @return The user entity.
     */
    public static User toEntity(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }
}
