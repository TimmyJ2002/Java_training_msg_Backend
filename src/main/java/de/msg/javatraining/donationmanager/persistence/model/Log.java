package de.msg.javatraining.donationmanager.persistence.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="action")
    private String action;

    @Column(name="severity")
    private String severity;

    @Column(name = "message")
    private String message;

    @Column(name= "username")
    private String username;

    @Column(name = "time")
    private LocalDateTime time;

    public Log() {
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
