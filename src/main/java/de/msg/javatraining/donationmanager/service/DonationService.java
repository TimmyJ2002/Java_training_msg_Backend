package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.exception.DonationNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepositoryJPA;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    DonationRepositoryImpl donationRepository;

    @Autowired
    DonationRepositoryJPA donationRepositoryJPA;

    @Autowired
    UserRepositoryInterface userRepository;

    public List<Donation> findAll() {
        List<Donation> d = donationRepository.findAll();
        return d;
    }

    public Donation findById(Long id) {
        Optional<Donation> optionalDonation = donationRepositoryJPA.findById(id);

        if(optionalDonation.isPresent()){
            return optionalDonation.get();
        }
        else {
            throw new DonationNotFoundException("Donation not found with ID: " + id);
        }
    }

    public Donation approveDonation(Donation donation, User userThatAprroved) {
       donation.setApproved(true);
       donation.setApprovedBy(userThatAprroved);
       donation.setApproveDate(LocalDate.now());


       return donationRepositoryJPA.save(donation);

    }
}