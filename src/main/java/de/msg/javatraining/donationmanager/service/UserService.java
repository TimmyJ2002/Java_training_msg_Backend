package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.WebSecurityConfig;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserDTO;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static de.msg.javatraining.donationmanager.persistence.model.DTOs.UserMapper.mapUserDTOToUser;

@Service
public class UserService {

    @Autowired
    private UserRepositoryInterface userRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;


    public User createUser(UserDTO userDTO){
        validateUserInput(userDTO);


        //Converts userDto to user
        User user = mapUserDTOToUser(userDTO);

        //Username generation
        String generatedUsername = generateUniqueUsername(user.getFirstName(), user.getLastName());

        user.setUsername(generatedUsername);

        //Initial Password generation
        String initialPassword = generateInitialPassword();
        user.setPassword(initialPassword);

        //Send email
       // sendWelcomeEmail(user.getEmail(), initialPassword);

        //initial login count 0 & is_active status true
        user.setLoginCount(0);
        user.setActive(true);




        return userRepository.save(user);
    }



    private String generateInitialPassword() {
        UUID uuid = UUID.randomUUID();
        String initialPassword = uuid.toString().replace("-","").substring(0,10); // For 10 character long Password

        return initialPassword;
    }

    public String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = (lastName.substring(0, Math.min(5, lastName.length())) +
                firstName.substring(0, 1)).toLowerCase();

        int count = 1;
        String username = baseUsername;
        username = baseUsername + count;
        while (userRepository.existsByUsername(username)) {
            count++;
            username = baseUsername + count;
        }

        return username;
    }



    private void validateUserInput(UserDTO userDTO) {

    }
    //TODO: implement method
    private boolean checkPassword(Long userId, String password){
        return true;
    }

    public int changeUserPassword(User user, String newPassword) throws Exception {
        Long userId = user.getId();
        String userPassword = user.getPassword();

        boolean checkUserPassword = checkPassword(userId, userPassword);

        if(checkUserPassword) {
            userRepository.changeUserPassword(webSecurityConfig.passwordEncoder().encode(newPassword));
            return 1;
        }
        return 0;
    }

    public void updateLoginCount(Long userId, int newLoginCount) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setLoginCount(newLoginCount);
            userRepository.save(user);
        } else {
            System.out.println("FAILED TO UPDATE LOGINCOUNT!");
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
