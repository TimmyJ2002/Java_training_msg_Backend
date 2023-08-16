package de.msg.javatraining.donationmanager.controller;

import com.fasterxml.jackson.core.JsonParser;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;

@RestController
@RequestMapping("/users")
    public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) throws InstanceAlreadyExistsException {
        User newUser = userService.createUser(userDTO);
        System.out.println(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
