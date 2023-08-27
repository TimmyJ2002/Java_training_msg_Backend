package de.msg.javatraining.donationmanager.controller.app;


import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserWithIdDTO;
import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.LogService;
import de.msg.javatraining.donationmanager.service.UserService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
    public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/find_all")
    public List<UserWithIdDTO> findAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/save")
    public ResponseEntity<User> createUser(@NonNull HttpServletRequest request, @RequestBody UserDTO userDTO) {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        logService.create("create", "info", "User created", username, LocalDateTime.now());

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

    @PutMapping("/update/{id}")
    public User updateUser(@NonNull HttpServletRequest request, @PathVariable("id") Long id, @RequestBody UserWithIdDTO updateUserDTO){
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        logService.create("modify", "info", "User modified", username, LocalDateTime.now());

        return userService.updateUser(request, id, updateUserDTO);
    }


    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateDeactivateUser(@NonNull HttpServletRequest request, @PathVariable("id") Long id){
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        logService.create("activate", "info", "User activated", username, LocalDateTime.now());

        userService.activateDeactivateUser(id);
        return new ResponseEntity<>("Toggled Activation ",HttpStatus.OK);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }
}
