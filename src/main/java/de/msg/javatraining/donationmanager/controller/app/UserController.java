package de.msg.javatraining.donationmanager.controller.app;


import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserWithIdDTO;
import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
    public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find_all")
    public List<UserWithIdDTO> findAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {

        User newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/find_by_id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find_by_role/{role}")
    public List<User> findUserByRole(@PathVariable(name = "role") Role role){

        return userService.findAllUserByRoles(role);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserWithIdDTO updateUserDTO){
            return userService.updateUser(id, updateUserDTO);
    }


    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateDeactivateUser(@PathVariable("id") Long id){
        userService.activateDeactivateUser(id);
        return new ResponseEntity<>("Toggled Activation ",HttpStatus.OK);
    }

}
