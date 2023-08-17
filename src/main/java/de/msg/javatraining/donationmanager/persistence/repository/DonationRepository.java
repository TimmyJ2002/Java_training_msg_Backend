package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Donation;

import java.util.List;

public interface DonationRepository {

    void saveDonation(Donation donation);

    void deleteDonation(Donation donation);

    List<Donation> findAll();
}
