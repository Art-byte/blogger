package com.artbyte.blog.service;

import com.artbyte.blog.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User findUserById(String id);
    User findUserByUsername(String username);
    User updateUser(String id, User user);
    void createUser(User user);
    void disabledUser(String id);
    void enabledUser(String id);
}
