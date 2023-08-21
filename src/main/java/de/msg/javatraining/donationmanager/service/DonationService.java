package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepositoryInterface;

    public void addDonation(Donation donation) {
        donationRepositoryInterface.saveDonation(donation);
    }

    public void removeDonation(Donation donation) {
        donationRepositoryInterface.deleteDonation(donation);
    }

    public List<Donation> findAll() {
        return donationRepositoryInterface.findAll();
    }

    public void updateDonation(int oldDonationID, int amount, String currency, Campaign campaign, Donator donator, String notes) {
        donationRepositoryInterface.updateDonation(oldDonationID, amount, currency, campaign, donator, notes);
    }

    public Donation findByID(int ID) {
        return donationRepositoryInterface.findByID(ID);
    }

}
