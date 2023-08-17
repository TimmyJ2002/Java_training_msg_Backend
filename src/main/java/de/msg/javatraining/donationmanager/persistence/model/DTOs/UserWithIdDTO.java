package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithIdDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private int loginCount;
    private List<Role> roles;
    private boolean isActive;
}
