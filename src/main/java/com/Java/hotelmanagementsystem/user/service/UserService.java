package com.Java.hotelmanagementsystem.user.service;


import com.Java.hotelmanagementsystem.constants.UserConstants;
import com.Java.hotelmanagementsystem.user.model.User;
import com.Java.hotelmanagementsystem.user.repository.UserRepository;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(@NonNull User user) {
        //TODO: Validate for save
        return userRepository.save(user);
    }

    @Override
    public User findById(long id) throws Exception {
        //TODO: Handle Not Found Exception with its own http codes.
        return userRepository.findById(id)
                .orElseThrow(()->new Exception(UserConstants.NOT_FOUND));
    }

    @Override
    public String update(@NonNull User user) {
        long id = user.getId();

        return userRepository.findById(id).map(user1 -> {
            user1.setName(user.getName());
            user1.setEmail(user.getEmail());
            user1.setCid(user.getCid());
            user1.setPhone(user.getPhone());
            user1.setPassword(user.getPassword());
            userRepository.save(user1);
            return UserConstants.UPDATE_SUCCESSFUL;
        })
                .orElse(UserConstants.NOT_FOUND);
    }

    @Override
    public String deleteById(long id) {
        //TODO: Validate for deletion
        userRepository.deleteById(id);
        return UserConstants.DELETE_SUCCESSFUL;
    }
}
