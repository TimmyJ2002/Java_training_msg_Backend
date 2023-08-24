package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import jakarta.persistence.Column;

public class LogDto {

    private Long id;
    private String action;
    private String severity;
    private String message;

    public LogDto(Long id, String action, String severity, String message) {
        this.id = id;
        this.action = action;
        this.severity = severity;
        this.message = message;
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
}
