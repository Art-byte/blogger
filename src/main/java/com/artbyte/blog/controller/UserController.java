package com.artbyte.blog.controller;

import com.artbyte.blog.model.User;
import com.artbyte.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blogs")
public class UserController {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User userCreated = userRepository.save(user);
            logger.info("Usuario creado exitosamente");
            return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
        } catch (Exception e){
            logger.error("Error desconocido => {}", e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
