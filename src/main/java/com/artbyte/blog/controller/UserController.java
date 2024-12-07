package com.artbyte.blog.controller;

import com.artbyte.blog.exception.UserException;
import com.artbyte.blog.model.User;
import com.artbyte.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            List<User> userList = userService.getAllUsers();
            if(userList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("User list => {}", userList);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("Error connecting to the database => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        try{
            User user = userService.findUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (UserException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            logger.info("User created successfully");
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (UserException e) {
            logger.error("Error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/users/disabled/{id}")
    public ResponseEntity<Void> disabledUser(@PathVariable String id){
        try{
            userService.disabledUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/roles")
    public ResponseEntity<List<String>> getRoles(){
        try{
            List<String> rolesList = Arrays.asList("ADMIN", "WRITER");
            return new ResponseEntity<>(rolesList, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/enabled/{id}")
    public ResponseEntity<Void> enabledUser(@PathVariable String id){
        try{
            userService.enabledUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
