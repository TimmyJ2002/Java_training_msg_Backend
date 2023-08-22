package de.msg.javatraining.donationmanager.controller.auth;

import lombok.Data;

@Data
public class RequestChangePassword {
    private String oldPassword;
    private String newPassword;
}
