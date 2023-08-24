package de.msg.javatraining.donationmanager.persistence.model.DTOs;


import de.msg.javatraining.donationmanager.persistence.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    private String title;
    private String text;
    private LocalDate createdDate;
    private Boolean isRead;


}
