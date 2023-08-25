package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.exception.EmailAlreadyExistsException;
import de.msg.javatraining.donationmanager.exception.MobileNumberAlreadyExistsException;
import de.msg.javatraining.donationmanager.exception.UserNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepositoryInterface userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreateUser_ValidInput() {

        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("Doe");
        userDTO.setFirstName("John");
        userDTO.setEmail("test@example.com");
        userDTO.setMobileNumber("0700000000");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setLastName("Doe");
        savedUser.setFirstName("Joshua");
        savedUser.setEmail("test1@example.com");
        savedUser.setMobileNumber("0770000000");
        Role role = new Role();
        role.setName(ERole.ROLE_ADM);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        savedUser.setRoles(roles);
        userRepository.save(savedUser);

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByMobileNumber(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(savedUser);

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("0700000000", createdUser.getMobileNumber());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void testCreateUser_MobileNumberAlreadyExists() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setMobileNumber("0700000000");

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByMobileNumber(any())).thenReturn(true);

        assertThrows(MobileNumberAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void testFindById_UserExists() {

        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setActive(true);
        user.setLoginCount(3);

        Role role = new Role();
        role.setName(ERole.ROLE_ADM);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    public void testFindById_UserNotFound() {

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
    }

    // Add more test methods as needed for other scenarios.

}

