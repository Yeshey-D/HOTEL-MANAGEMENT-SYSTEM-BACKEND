package com.Java.hotelmanagementsystem.user.repository;

import com.Java.hotelmanagementsystem.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
