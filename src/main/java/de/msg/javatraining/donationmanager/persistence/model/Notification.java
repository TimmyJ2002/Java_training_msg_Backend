package de.msg.javatraining.donationmanager.persistence.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="text")
    private String text;

    @Column(name="createdDate")
    private LocalDate createdDate;

    @Column(name="isRead")
    private boolean isRead;

//    @ManyToOne(fetch=FetchType.LAZY)
////    @JoinColumn(name="receiver_id")
////    private User notificationReceiver;


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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
