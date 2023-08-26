package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.exception.EmailAlreadyExistsException;
import de.msg.javatraining.donationmanager.exception.MobileNumberAlreadyExistsException;
import de.msg.javatraining.donationmanager.exception.UserNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserWithIdDTO;
import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import de.msg.javatraining.donationmanager.persistence.repository.impl.RoleRepositoryInterfaceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static de.msg.javatraining.donationmanager.persistence.model.DTOs.UserMapper.mapUserToUserWithIdDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepositoryInterface userRepository;

    @Mock
    private RoleRepositoryInterfaceImpl roleRepositoryInterface;

    @InjectMocks
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private NotificationService notificationService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
//    @Test
//    public void testCreateUser_ValidInput() {
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setLastName("Doe");
//        userDTO.setFirstName("John");
//        userDTO.setEmail("test@example.com");
//        userDTO.setMobileNumber("0700000000");
//
//        User savedUser = new User();
//        savedUser.setId(1L);
//        savedUser.setLastName("Doe");
//        savedUser.setFirstName("Joshua");
//        savedUser.setEmail("test1@example.com");
//        savedUser.setMobileNumber("0770000000");
//        Role role = new Role();
//        role.setName(ERole.ROLE_ADM);
//        List<Role> roles = new ArrayList<>();
//        roles.add(role);
//        savedUser.setRoles(roles);
//        userRepository.save(savedUser);
//
//        when(userRepository.existsByEmail(any())).thenReturn(false);
//        when(userRepository.existsByMobileNumber(any())).thenReturn(false);
//        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
//        when(userRepository.save(any())).thenReturn(savedUser);
//
//        User createdUser = userService.createUser(userDTO);
//
//        assertNotNull(createdUser);
//        assertEquals("test@example.com", createdUser.getEmail());
//        assertEquals("0700000000", createdUser.getMobileNumber());
//        assertEquals("encodedPassword", createdUser.getPassword());
//    }

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

//    @Test
//    void testCreateUser() {
//        // Arrange
//        UserDTO userDTO = new UserDTO();
//        userDTO.setFirstName("John");
//        userDTO.setLastName("Doe");
//        userDTO.setEmail("john.doe@example.com");
//        userDTO.setMobileNumber("1234567890");
//        userDTO.setRoles(new String[]{"ROLE_REP", "ROLE_ADM"}); // Set userDTO properties as needed
//        User user = new User(); // Set user properties as needed
//       // List<Role> roles = new ArrayList<>(); // Set roles as needed
//
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setEmail(userDTO.getEmail());
//        user.setMobileNumber(userDTO.getMobileNumber());
//
//
//        when(userService.generateUniqueUsername(anyString(), anyString()))
//                .thenReturn("mockedUsername")
//                .thenReturn("mockedUsername2");
//        when(userService.generateInitialPassword()).thenReturn("mockedPassword");
//       // when(userService.processRoles(anyList())).thenReturn(roles);
//        when(userService.passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//
//        // Act
//        User result = userService.createUser(userDTO);
//
//        //
//        //  that the result user matches your expectations
//        // For example:
//        assertEquals("generatedUsername", result.getUsername());
//        assertEquals("encodedPassword", result.getPassword());
//        assertEquals(-1, result.getLoginCount());
//        assertEquals("mockedPassword", result.getPassword());
//        assertTrue(result.isActive());
//
//        // Verify that the necessary methods were called with correct arguments
//        //verify(userRepository).save(user);
//       // verify(notificationService).createNotification(any(NotificationDTO.class), eq("generatedUsername"));
//       // verify(emailService).sendWelcomeEmail(eq(user.getEmail()), eq("initialPassword"));
//    }
@Test
public void testValidateUserInput_AllChecksPassed() {
    UserDTO userDTO = new UserDTO();
    userDTO.setFirstName("John");
    userDTO.setLastName("Doe");
    userDTO.setEmail("john@example.com");
    userDTO.setMobileNumber("1234567890");

    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(userRepository.existsByMobileNumber(anyString())).thenReturn(false);

    boolean result = userService.validateUserInput(userDTO);

    assertTrue(result); // Ensure the method returns true

    verify(userRepository, times(1)).existsByEmail(anyString());
    verify(userRepository, times(1)).existsByMobileNumber(anyString());
}

    @Test
    public void testValidateUserInput_EmailAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Jane");
        userDTO.setLastName("Smith");
        userDTO.setEmail("jane@example.com");
        userDTO.setMobileNumber("9876543210");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.validateUserInput(userDTO));

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, never()).existsByMobileNumber(anyString());
    }

    @Test
    public void testValidateUserInput_MobileNumberAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Alice");
        userDTO.setLastName("Johnson");
        userDTO.setEmail("alice@example.com");
        userDTO.setMobileNumber("5555555555");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByMobileNumber(anyString())).thenReturn(true);

        assertThrows(MobileNumberAlreadyExistsException.class, () -> userService.validateUserInput(userDTO));

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).existsByMobileNumber(anyString());
    }

    @Test
    public void testValidateUserInput_NullName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setMobileNumber("1234567890");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByMobileNumber(anyString())).thenReturn(false);

        assertThrows(NullPointerException.class, () -> userService.validateUserInput(userDTO));

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).existsByMobileNumber(anyString());
    }

//    @Test
//    public void testProcessRoles() {
//        String[] roleNames = {"ROLE_ADM", "ROLE_CEN", "INVALID_ROLE"};
//
//        Role roleAdmin = new Role();
//        roleAdmin.setId(1);
//        roleAdmin.setName(ERole.ROLE_ADM);
//
//        Role roleCenter = new Role();
//        roleCenter.setId(2);
//        roleCenter.setName(ERole.ROLE_CEN);
//
//        when(roleRepositoryInterface.findByName(ERole.ROLE_ADM)).thenReturn(roleAdmin);
//        when(roleRepositoryInterface.findByName(ERole.ROLE_CEN)).thenReturn(roleCenter);
//       // when(roleRepositoryInterface.findByName(ERole.ROLE_REP)).thenReturn(null); // Simulate not found
//
//        List<Role> processedRoles = userService.processRoles(roleNames);
//
//        assertEquals(2, processedRoles.size()); // Check if valid roles are added
//
//        assertTrue(processedRoles.contains(roleAdmin));
//        assertTrue(processedRoles.contains(roleCenter));
//
//        verify(roleRepositoryInterface, times(1)).findByName(ERole.ROLE_ADM);
//        verify(roleRepositoryInterface, times(1)).findByName(ERole.ROLE_CEN);
//        verify(roleRepositoryInterface, times(1)).findByName(ERole.ROLE_REP);
//    }

    @Test
    public void testGenerateInitialPassword() {


        String initialPassword = userService.generateInitialPassword();

        assertNotNull(initialPassword);
        assertEquals(10, initialPassword.length());

        // Validate that the password contains only hexadecimal characters
        for (char c : initialPassword.toCharArray()) {
            assertTrue(Character.isDigit(c) || ('a' <= c && c <= 'f'));
        }
    }


    @Test
    public void testGenerateUniqueUsername() {

        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        // Test when base username is unique
        String uniqueUsername = userService.generateUniqueUsername("John", "Doe");
        assertEquals("doej", uniqueUsername);

        // Test when base username exists and requires incrementing
        when(userRepository.existsByUsername("doej1")).thenReturn(true);
        when(userRepository.existsByUsername("doej2")).thenReturn(false);

        uniqueUsername = userService.generateUniqueUsername("John", "Doe");
        assertEquals("doej", uniqueUsername);
    }

    @Test
    public void testChangeUserPassword() {

        User user = new User();
        user.setId(1L);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");


        userService.changeUserPassword(1L, "newPassword");


        verify(userRepository, times(1)).save(user);


        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    public void testUpdateLoginCount() {

        User user = new User();
        user.setId(1L);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        userService.updateLoginCount(1L, 5);


        verify(userRepository, times(1)).save(user);


        assertEquals(5, user.getLoginCount());
    }

    @Test
    public void testDeactivateUser() {

        User user = new User();
        user.setId(1L);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        userService.deactivateUser(1L, false);


        verify(userRepository, times(1)).save(user);


        assertFalse(user.isActive());
    }

    @Test
    public void testParseJwtWithoutToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        String jwtToken = userService.parseJwt(request);

        assertNull(jwtToken);
    }

    @Test
    public void testParseJwtWithToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer myJwtToken");

        String jwtToken = userService.parseJwt(request);

        assertEquals("Bearer myJwtToken", jwtToken);
    }

    @Test
    public void testFindByIdWhenUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(userId);

        assertEquals(userId, foundUser.getId());
    }

    @Test
    public void testFindByIdWhenUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    public void testUpdateUser() {
        UserWithIdDTO userWithIdDTO = new UserWithIdDTO();


        userWithIdDTO.setId(1L);
        userWithIdDTO.setFirstName("NewFirstName");
        userWithIdDTO.setLastName("NewLastName");
        userWithIdDTO.setEmail("newemail@example.com");
        userWithIdDTO.setMobileNumber("1234567890");
        userWithIdDTO.setRoles(new ArrayList<>());
        userWithIdDTO.setActive(true);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");
        existingUser.setEmail("oldemail@example.com");
        existingUser.setMobileNumber("9876543210");
        existingUser.setRoles(new ArrayList<>());
        existingUser.setActive(false);

        when(request.getHeader("Authorization")).thenReturn("your_mocked_jwt");
        when(jwtUtils.getUserNameFromJwtToken(anyString())).thenReturn("mocked_username");


        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateUser(request, 1L, userWithIdDTO);

        assertEquals("NewFirstName", updatedUser.getFirstName());
        assertEquals("NewLastName", updatedUser.getLastName());
        assertEquals("newemail@example.com", updatedUser.getEmail());
        assertEquals("1234567890", updatedUser.getMobileNumber());
        assertTrue(updatedUser.isActive());


    }

    @Test
    public void testCreateUser_EmailAlreadyExists2() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setMobileNumber("123456789");
        userDTO.setRoles(new String[]{"ROLE_ADM"});

        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act and Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userDTO));

        // Verify method invocations
        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, never()).existsByMobileNumber(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(emailService, never()).sendWelcomeEmail(any(), any());
        verify(notificationService, never()).createNotification(any(), any());
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setMobileNumber("123456789");
        userDTO.setRoles(new String[]{"ROLE_ADM"});

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByMobileNumber(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(roleRepositoryInterface.findByName(any(ERole.class))).thenReturn(new Role());

        // Act
        User createdUser = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertTrue(createdUser.isActive());
        assertEquals(0, createdUser.getLoginCount());
        // ... assert other properties and interactions

        // Verify method invocations
        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).existsByMobileNumber(any());
        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(any());
        verify(emailService, times(1)).sendWelcomeEmail(eq("john@example.com"), eq("encodedPassword"));
        verify(notificationService, times(2)).createNotification(any(), any()); // Two notifications created
    }

    @Test
    public void testUpdateUser2_Success() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john@example.com");
        existingUser.setMobileNumber("123456789");
        existingUser.setActive(true);
        List<Role> roles = new ArrayList<>();

        existingUser.setRoles(roles);

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = new User();
        updatedUser.setId(existingUser.getId());
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setMobileNumber("987654321");
        updatedUser.setActive(false);
        List<Role> updatedRoles = new ArrayList<>();

        updatedUser.setRoles(updatedRoles);

        // Act
        User result = userService.updateUser2(updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("987654321", result.getMobileNumber());
        assertFalse(result.isActive());
        assertEquals(updatedRoles, result.getRoles());

        // Verify method invocations
        verify(userRepository, times(1)).findById(existingUser.getId());
        verify(userRepository, times(1)).save(existingUser);
    }


    @Test
    public void testGetAllUsers_Success() {
        // Arrange
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john@example.com");
        userList.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane@example.com");
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserWithIdDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        UserWithIdDTO resultUser1 = result.get(0);
        assertEquals(user1.getId(), resultUser1.getId());
        assertEquals(user1.getFirstName(), resultUser1.getFirstName());
        assertEquals(user1.getLastName(), resultUser1.getLastName());
        assertEquals(user1.getEmail(), resultUser1.getEmail());

        UserWithIdDTO resultUser2 = result.get(1);
        assertEquals(user2.getId(), resultUser2.getId());
        assertEquals(user2.getFirstName(), resultUser2.getFirstName());
        assertEquals(user2.getLastName(), resultUser2.getLastName());
        assertEquals(user2.getEmail(), resultUser2.getEmail());

        // Verify method invocations
        verify(userRepository, times(1)).findAll();
    }



}

