package com.Java.hotelmanagementsystem.user.service;

import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.model.UserDTO;
import com.Java.hotelmanagementsystem.util.IGenericCrudService;

public interface IUserService extends IGenericCrudService<User, UserDTO> {
    /**
     * Fetches the authenticated instructor info.
     *
     * @return The instructor dto
     */
    UserDTO fetchSelfInfo();
}
