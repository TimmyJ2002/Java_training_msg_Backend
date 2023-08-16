package de.msg.javatraining.donationmanager.controller.auth;

import lombok.Data;

@Data
public class RequestChangePassword {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
