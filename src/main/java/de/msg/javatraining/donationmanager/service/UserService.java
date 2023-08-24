package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.config.security.WebSecurityConfig;
import de.msg.javatraining.donationmanager.exception.EmailAlreadyExistsException;
import de.msg.javatraining.donationmanager.exception.MobileNumberAlreadyExistsException;
import de.msg.javatraining.donationmanager.exception.UserNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserWithIdDTO;
import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.impl.RoleRepositoryInterfaceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static de.msg.javatraining.donationmanager.persistence.model.DTOs.UserMapper.*;

@Service
public class UserService {

    @Autowired
    private UserRepositoryInterface userRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Autowired
    private RoleRepositoryInterfaceImpl roleRepositoryInterface;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    NotificationService notificationService;


    public User createUser(UserDTO userDTO) throws IllegalArgumentException{
        validateUserInput(userDTO);

        //Converts userDto to user
        User user = mapUserDTOToUser(userDTO);

        //Username generation
        String generatedUsername = generateUniqueUsername(user.getFirstName(), user.getLastName());
        user.setUsername(generatedUsername);

        //Initial Password generation
       String initialPassword = generateInitialPassword();

        //String initialPassword = "12345678910";
        user.setPassword(initialPassword);
        sendWelcomeEmail(user.getEmail(), initialPassword);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //initial login count -1 & is_active status true
        user.setLoginCount(-1);
        user.setActive(true);

        List<Role> roles = processRoles(userDTO.getRoles());
        user.setRoles(roles);


            NotificationDTO notificationDTO = new NotificationDTO();

            notificationDTO.setTitle("Welcome to Donation Manager!");
            notificationDTO.setText(user.getFirstName()+ "!\n" +
                    "Your new account is all set up and ready for you to explore." +
                    "Best regards,\n" +
                    "The Team 4"
            );
            notificationDTO.setCreatedDate(LocalDate.now());
            notificationDTO.setIsRead(false);

            notificationService.createNotification(notificationDTO, user.getUsername());




        return userRepository.save(user);
    }

    private void sendWelcomeEmail(String email, String initialPassword) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Welcome to Donation Manager");
        mailMessage.setText("Welcome to our Donation Manager MSG! Your initial password is: " + initialPassword + "\n Your password will need to be changed at the initial Login.");
        try {
            javaMailSender.send(mailMessage);
        }catch (MailException e){
            System.err.println("Error sending email:" + e);
        }
    }


    // inside methods
    private List<Role> processRoles(String[] roleNames) {
        List<Role> roles = new ArrayList<>();
        for (String roleName : roleNames) {
            try {
                ERole eRole = ERole.valueOf(roleName); // Convert roleName to ERole enum
                Role role = roleRepositoryInterface.findByName(eRole); // Find Role by ERole enum
                if (role != null) {
                    roles.add(role);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Something wrong with setting the roles");
            }
        }
        return roles;
    }


    private String generateInitialPassword() {
        UUID uuid = UUID.randomUUID();
        String initialPassword = uuid.toString().replace("-","").substring(0,10); // For 10 character long Password

        return initialPassword;
    }

    public String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = (lastName.substring(0, Math.min(5, lastName.length())) +
                firstName.substring(0, 1)).toLowerCase();


        if (!userRepository.existsByUsername(baseUsername)) {
            return baseUsername;
        }

        Integer count = 1;
        String username = baseUsername;
        username = baseUsername + count;
        while (userRepository.existsByUsername(username)) {
            count++;
            username = baseUsername + count;
        }

        return username;
    }



    private boolean validateUserInput(UserDTO userDTO) {
        // Check if email is already existing
        boolean isEmailExisting = userRepository.existsByEmail(userDTO.getEmail());
        if (isEmailExisting) {
            throw new EmailAlreadyExistsException("Email already exists in the database");
        }

        // Check if mobile number is already existing
        boolean isMobileNumberExisting = userRepository.existsByMobileNumber(userDTO.getMobileNumber());
        if (isMobileNumberExisting) {
            throw new MobileNumberAlreadyExistsException("Mobile number already exists in the database");
        }

        // All checks passed
        return true;

    }

    private boolean checkPassword(Long userId, String password){
        return true;
    }


    @Transactional
    public void changeUserPassword(Long userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    @Transactional
    public void updateLoginCount(Long userId, int newLoginCount) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLoginCount(newLoginCount);
            userRepository.save(user);
        }
    }

    @Transactional
    public void deactivateUser (Long userId, boolean status){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(status);
            userRepository.save(user);
        }
    }
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    public User updateUser(HttpServletRequest request,Long id, UserWithIdDTO userWithIdDTO) throws IllegalArgumentException{
        String jwt = parseJwt(request);
        String userNameThatEdited = jwtUtils.getUserNameFromJwtToken(jwt);

        validateUserInputForUpdate(id, userWithIdDTO);

        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        User user = existingUser.get();

        if (userWithIdDTO.getFirstName() != null && userWithIdDTO.getFirstName().length() < 255) {
            user.setFirstName(userWithIdDTO.getFirstName());
        }
        if (userWithIdDTO.getLastName() != null && userWithIdDTO.getLastName().length() < 255) {
            user.setLastName(userWithIdDTO.getLastName());

        }
        if (userWithIdDTO.getEmail() != null && userWithIdDTO.getEmail().length() < 255) {
            user.setEmail(userWithIdDTO.getEmail());
        }
        if (userWithIdDTO.getMobileNumber() != null && userWithIdDTO.getMobileNumber().length() < 255) {
            user.setMobileNumber(userWithIdDTO.getMobileNumber());
        }
        if (userWithIdDTO.getRoles() != null) {
            user.setRoles(userWithIdDTO.getRoles());
        }
        if (userWithIdDTO.getActive() != null) {
            user.setActive(userWithIdDTO.getActive());
        }

        //Notification part for both users
        NotificationDTO notificationForEditedUser = new NotificationDTO();
        notificationForEditedUser.setTitle("Account Updated");
        notificationForEditedUser.setText("Your User Details we're changed by a User Manager! New credentials are: "
                + user.getFirstName() + " " + user.getLastName() + " Email: " + user.getEmail() + "Roles" + getRoleNames(user.getRoles()));
        notificationForEditedUser.setCreatedDate(LocalDate.now());
        notificationForEditedUser.setIsRead(false);

        notificationService.createNotification(notificationForEditedUser, user.getUsername());


        NotificationDTO notificationForUserThatEdited = new NotificationDTO();
        notificationForUserThatEdited.setTitle("You Updated An Account Succesfully!");
        notificationForUserThatEdited.setText("You edited the user: " + user.getUsername() + ". Now the user is: "
        +  user.getFirstName() + " " + user.getLastName() + " , Email: " + user.getEmail());
        notificationForUserThatEdited.setCreatedDate(LocalDate.now());
        notificationForUserThatEdited.setIsRead(false);

        notificationService.createNotification(notificationForUserThatEdited, userNameThatEdited);
        //

        return userRepository.save(user);

    }

    private String getRoleNames(List<Role> roles) {
        StringBuilder roleNames = new StringBuilder();
        for (Role role : roles) {
            roleNames.append(role.getName()).append(", ");
        }
        // Remove the trailing comma and space
        if (roleNames.length() > 0) {
            roleNames.delete(roleNames.length() - 2, roleNames.length());
        }
        return roleNames.toString();
    }

    public User updateUser2(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Long id = user.getId(); // Assuming id is present in the User object

        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }

        User existing = existingUser.get();

        if (user.getFirstName() != null && user.getFirstName().length() < 255) {
            existing.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && user.getLastName().length() < 255) {
            existing.setLastName(user.getLastName());
        }
        if (user.getEmail() != null && user.getEmail().length() < 255) {
            existing.setEmail(user.getEmail());
        }
        if (user.getMobileNumber() != null && user.getMobileNumber().length() < 255) {
            existing.setMobileNumber(user.getMobileNumber());
        }
        if (user.getRoles() != null) {
            existing.setRoles(user.getRoles());
        }
        if (user.getIsActive() != existing.isActive()) {
            existing.setActive(user.getIsActive());
        }


        return userRepository.save(existing);
    }



    private boolean validateUserInputForUpdate(Long id, UserWithIdDTO userWithIdDTO) {
        if (userWithIdDTO.getEmail() != null && !userWithIdDTO.getEmail().isEmpty()) {
            boolean isEmailExisting = userRepository.existsByEmailAndIdNot(userWithIdDTO.getEmail(), id);
            if (isEmailExisting) {
                throw new EmailAlreadyExistsException("Another User with this Email already exists in the database");
            }
        }

        if (userWithIdDTO.getMobileNumber() != null && !userWithIdDTO.getMobileNumber().isEmpty()) {
            // Check if mobile number is already existing
            boolean isMobileNumberExisting = userRepository.existsByMobileNumberAndIdNot(userWithIdDTO.getMobileNumber(), id);
            if (isMobileNumberExisting) {
                throw new MobileNumberAlreadyExistsException("Another User with this mobile number already exists in the database");
            }
        }

        return true;
    }

    public List<UserWithIdDTO> getAllUsers() {
       List<User> userList = userRepository.findAll();
       return userList.stream().map(user -> mapUserToUserWithIdDTO(user)).collect(Collectors.toList());
    }

    public void activateDeactivateUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        User user = userOptional.get();
        user.setActive(!user.getIsActive());

//        if(!user.getIsActive()){
            NotificationDTO notificationDTO = new NotificationDTO();

            notificationDTO.setTitle("Account Deactivated");
            notificationDTO.setText("Account was deactivated for user with ID: " + id);
            notificationDTO.setCreatedDate(LocalDate.now());
            notificationDTO.setIsRead(false);

            List<UserWithIdDTO> users = this.getAllUsers();

            for (UserWithIdDTO u : users){
                if (u.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADM))){
                    notificationService.createNotification(notificationDTO, u.getUsername());
                    System.out.println("Notification created");
                }
            }
//        }
        userRepository.save(user);

        mapUserToUserDTO(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
