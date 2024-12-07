package com.artbyte.blog.service.impl;

import com.artbyte.blog.enums.StatusSystem;
import com.artbyte.blog.exception.UserException;
import com.artbyte.blog.model.User;
import com.artbyte.blog.repository.UserRepository;
import com.artbyte.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));

    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Username " + username + " not found"));
    }

    @Override
    public User updateUser(String id, User user) {
        User userObj = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        userObj.setName(user.getName());
        userObj.setLastName(user.getLastName());
        userObj.setEmail(user.getEmail());
        userObj.setUsername(user.getUsername());
        userObj.setStatus(user.getStatus());
        userObj.setSocialMedia(user.getSocialMedia());
        return userRepository.save(userObj);
    }

    @Override
    public void createUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserException("This user already exists");
        }
        user.setStatus(StatusSystem.ACTIVE.name());
        userRepository.save(user);
    }

    @Override
    public void disabledUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        user.setStatus(StatusSystem.INACTIVE.name());
        userRepository.save(user);
    }

    @Override
    public void enabledUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        user.setStatus(StatusSystem.ACTIVE.name());
        userRepository.save(user);
    }
}
