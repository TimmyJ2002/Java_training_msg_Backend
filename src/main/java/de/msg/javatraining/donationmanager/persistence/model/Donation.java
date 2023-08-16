package de.msg.javatraining.donationmanager.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name="donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="amount")
    @NonNull
    private int amount;

    @Column(name = "currency")
    @NonNull
    private String currency;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="campaign_id")
    @JsonBackReference
    private Campaign campaign;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="createdby_id")
    @JsonBackReference
    private User createdBy;

    @Column(name="createdDate")
    private LocalDate createdDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="donator_id")
    @JsonBackReference
    private Donator donator;

    @Column(name="approved")
    private boolean approved;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="approvedby_id")
    @JsonBackReference
    private User approvedBy;

    @Column(name="approveDate")
    private LocalDate approveDate;

    @Column(name="notes")
    private String notes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
