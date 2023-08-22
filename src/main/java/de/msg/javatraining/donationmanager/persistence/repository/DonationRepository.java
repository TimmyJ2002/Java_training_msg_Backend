package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository {

    void saveDonation(Donation donation);

    void deleteDonation(Donation donation);

    List<Donation> findAll();

    void updateDonation(int oldDonationID, int amount, String currency, Campaign campaign, Donator donator, String notes);

    Donation findByID(long ID);

}
