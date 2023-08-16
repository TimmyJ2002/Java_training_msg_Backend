package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonatorRepositoryImpl;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.List;
import java.util.Optional;

@Service
public class DonatorService {

    @Autowired
    private DonatorRepositoryImpl donatorRepository;

    public void saveDonator(Donator d){
        try {
            donatorRepository.saveDonator(d);
        }
        catch(UnexpectedRollbackException e){
            e.printStackTrace();
        }
    }
    public List<Donator> findAll(){
        List<Donator> d = donatorRepository.findAll();
        return d;
    }


    public Optional<Donator> findById(int id) {
        return Optional.ofNullable(donatorRepository.findByID(id));
    }

    public Donator findById(long id) {
        return donatorRepository.findByID(id);

    }
    public void editDonator(long id, Donator d){
        donatorRepository.editDonator(id,d);
    }

    public void specialDeleteDonator(Donator d){
        donatorRepository.specialDeleteDonator(d);
    }
}
