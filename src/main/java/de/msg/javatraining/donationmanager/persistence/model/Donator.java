package de.msg.javatraining.donationmanager.persistence.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name="donator")
public class Donator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstname", nullable = false)
    @NotBlank
    private String firstName;

    @Column(name="lastname", nullable = false)
    @NotBlank
    private String lastName;

    @Column(name="additionalname")
    private String additionalName;

    @Column(name="maidenname")
    private String maidenName;

    @Column(name="isactive")
    private boolean isActive;

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void specialDelete(){

        this.setFirstName("Unknown");
        this.setLastName("Unknown");
        this.setMaidenName("");
        this.setAdditionalName("");
        this.setActive(false);

    }


}


