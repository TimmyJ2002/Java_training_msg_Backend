package de.msg.javatraining.donationmanager.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="text")
    private String text;

    @Column(name="createdDate")
    private LocalDate createdDate;

    @Column(name="isRead")
    private Boolean isRead;

    @Column(name = "username")
    private String notificationReceiverUsername;

    public String getNotificationReceiverUsername() {
        return notificationReceiverUsername;
    }


    public void setNotificationReceiverUsername(String notificationReceiverUsername) {
        this.notificationReceiverUsername = notificationReceiverUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
