package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.exception.InvalidDataException;
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

    public void saveDonator(Donator d) throws InvalidDataException{
        try {
            validateDonator(d);
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


    public Donator findById(long id) {
        return donatorRepository.findByID(id);

    }
    public void editDonator(long id, Donator d) throws InvalidDataException, NullPointerException{
        validateDonator(d);
        exists(id);
        donatorRepository.editDonator(id,d);
    }

    public void specialDeleteDonator(Donator d){
        donatorRepository.specialDeleteDonator(d);
    }
    public boolean validateDonator(Donator d){
        if( d.getLastName().equals("") || d.getFirstName().equals(""))
            throw new InvalidDataException("Firstname or lastname field is empty.");
        return true;
    }
    public boolean exists(long id) {
        if(!donatorRepository.existsById(id))
            throw new NullPointerException();
        return true;
    }
}
