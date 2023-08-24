package de.msg.javatraining.donationmanager.controller.auth;


import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.config.security.WebSecurityConfig;
import de.msg.javatraining.donationmanager.exception.UserNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserWithIdDTO;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.NotificationService;
import de.msg.javatraining.donationmanager.service.UserDetailsImpl;
import de.msg.javatraining.donationmanager.service.UserService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepositoryInterface userRepositoryInterface;

  @Autowired
  RoleRepositoryInterface roleRepositoryInterface;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserService userService;

  @Autowired
  WebSecurityConfig webSecurityConfig;

  @Autowired
  NotificationService notificationService;

  private int loginCounter = 0;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      User user = userService.findUserByUsername(userDetails.getUsername());

      if (userDetails.getLoginCount() == -1) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\": \"Password change required\"}");
      }


    System.out.println(userDetails.getUsername() + " " + userDetails.getEmail());
    String jwt = jwtUtils.generateJwtToken(userDetails, user);

      System.out.println("Token:" + jwt);

      List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
              .collect(Collectors.toList());

      return ResponseEntity.ok(new SignInResponse(jwt, userDetails.getId(),
              userDetails.getUsername(), userDetails.getEmail(), userDetails.getLoginCount(), roles));
    }
    catch (Exception e){
      loginCounter++;
      System.out.println("Login counter: " + loginCounter);

      User user = userService.findUserByUsername(loginRequest.getUsername());

      if (loginCounter >= 5 && user != null){
        user.setActive(false);
        userService.updateUser2(user);
        System.out.println("User deactivated");
        notificationService.createAccountDeactivatedNotification(user.getUsername());
        System.out.println("Notification created");
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"An error occurred\"}");
    }
  }


  @PostMapping("/change-password")
  public ResponseEntity<String> changeUserPassword(@RequestBody RequestChangePassword requestChangePassword, HttpServletRequest request) {
    try {
      String jwt = userService.parseJwt(request);
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      System.out.println("Token:" + jwt);
      User user = userService.findUserByUsername(username);

      if (user != null) {
        String newPassword = requestChangePassword.getNewPassword();
        userService.changeUserPassword(user.getId(), newPassword);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Password changed successfully\"}");
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"An error occurred\"}");
    }
  }


  @PutMapping("/update-login-count")
  public ResponseEntity<String> updateLoginCount(@RequestBody RequestLogincountUpdate requestLogincountUpdate, HttpServletRequest request) {
    try {
      String jwt = userService.parseJwt(request);
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      System.out.println(jwt);
      User user = userService.findUserByUsername(username);

      if (user != null) {
        int newLoginCount = requestLogincountUpdate.getNewLoginCount();
        userService.updateLoginCount(user.getId(), newLoginCount);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Login count updated successfully\"}");
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"An error occurred\"}");
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
    System.out.println("Received Authorization header: " + authorizationHeader);

    // Extract token from Authorization header
    String token = authorizationHeader.substring("Bearer ".length());
    System.out.println("Extracted Token: " + token);

    SecurityContextHolder.clearContext();

    // Invalidate the token
    jwtUtils.revokeToken(token);

    System.out.println(jwtUtils.revokedTokens);
    return ResponseEntity.ok("{\"message\": \"Logged out successfully\"}");
  }

  @GetMapping("/get-username")
  public String getUsernameFromToken(@RequestParam String token) {
    String username = jwtUtils.getUserNameFromJwtToken(token);
    return username;
  }

}
