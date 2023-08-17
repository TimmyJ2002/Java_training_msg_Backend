package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        userWithIdDTO.setId(user.getId()); //id
        userWithIdDTO.setUsername(user.getUsername()); //username
        userWithIdDTO.setFirstName(user.getFirstName()); //firstName
        userWithIdDTO.setLastName(user.getLastName()); //lastName
        userWithIdDTO.setEmail(user.getEmail()); // email
        userWithIdDTO.setMobileNumber(user.getMobileNumber()); //mobileNumber
        userWithIdDTO.setLoginCount(user.getLoginCount());//loginCount
        userWithIdDTO.setRoles(user.getRoles());// List<roles>
        userWithIdDTO.setActive(user.getIsActive());//isActive
        return userWithIdDTO;
    }

//List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//        .collect(Collectors.toList());
//    donators.stream()
//            .map(donatorMapper::donatorToDto)
//                .collect(Collectors.toList());


}





