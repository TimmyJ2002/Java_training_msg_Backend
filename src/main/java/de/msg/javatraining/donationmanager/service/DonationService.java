package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DonationService {

    @Autowired
    DonationRepositoryImpl donationRepository;

    public List<Donation> findAll(){
        List<Donation> d = donationRepository.findAll();
        return d;
    }
}
