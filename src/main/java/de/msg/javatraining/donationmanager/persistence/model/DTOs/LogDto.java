package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class LogDto {

    private Long id;
    private String action;
    private String severity;
    private String message;
    private String username;
    private LocalDateTime time;

    public LogDto() {
    }

    public LogDto(Long id, String action, String severity, String message, String username, LocalDateTime time) {
        this.id = id;
        this.action = action;
        this.severity = severity;
        this.message = message;
        this.username = username;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
