package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User mapUserDTOToUser(UserDTO userDTO){
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobileNumber());

        return user;
    }


    public static UserDTO mapUserToUserDTO(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setMobileNumber(user.getMobileNumber());

            return userDTO;
    }

    public static UserWithIdDTO mapUserToUserWithIdDTO(User user){
        UserWithIdDTO userWithIdDTO = new UserWithIdDTO();
        userWithIdDTO.setId(user.getId());
        userWithIdDTO.setFirstName(user.getFirstName());
        userWithIdDTO.setLastName(user.getLastName());
        userWithIdDTO.setEmail(user.getEmail());
        userWithIdDTO.setMobileNumber(user.getMobileNumber());

        return userWithIdDTO;
    }




}





