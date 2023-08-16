package de.msg.javatraining.donationmanager.persistence.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name="campaign",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "name"),
})

public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="purpose")
    @NonNull
    private String purpose;

    @Column(name="name")
    @NonNull
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "campaign",
            fetch = FetchType.EAGER
    )
    private List<Donation> donationList;

    public Campaign() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(@NonNull String purpose) {
        this.purpose = purpose;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public List<Donation> getDonationList() {
        return donationList;
    }

    public void setDonationList(List<Donation> donationList) {
        this.donationList = donationList;
    }
}
