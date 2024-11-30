package com.artbyte.blog.service.impl;

import com.artbyte.blog.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User findUserById(String id);
    User findUserByUsername(String username);
    User updateUser(String id, User user);
    void createUser(User user);
    void deleteUser(String id);
}
