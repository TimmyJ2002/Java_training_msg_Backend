package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository {

    void saveDonation(Donation donation);

    void deleteDonation(long id);

    List<Donation> findAll();

    void updateDonation(int oldDonationID, int amount, String currency, Campaign campaign, Donator donator, String notes);

    Donation findByID(long ID);

    @Modifying
    @Query("UPDATE Donation d SET d.approved = true WHERE d.id = :id")
    void approveDonation (@Param("donationId") Long donationId);

}
