package de.msg.javatraining.donationmanager.persistence.repository.impl;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.repository.DonatorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class DonatorRepositoryImpl implements DonatorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveDonator(Donator u) {
        u.setActive(true);
        try {
            em.persist(u);
        }
        catch (PropertyValueException e)
        {
            //adaug la tabela de log
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDonator(Donator u) {
        em.remove(u);
    }

    @Override
    public List<Donator> findAll() {
        return em.createQuery("select u from Donator u", Donator.class).getResultList();
    }

    @Override
    public Donator findByID(long DonatorID) {
        return em.find(Donator.class, DonatorID);
    }

    @Override
    public void editDonator(long id, Donator d){
        Donator existingDonator = em.find(Donator.class, id);
        if (existingDonator != null) {
                if (!d.getFirstName().equals(""))
                    existingDonator.setFirstName(d.getFirstName());
                if (!d.getLastName().equals(""))
                    existingDonator.setLastName(d.getLastName());
                existingDonator.setMaidenName(d.getMaidenName());
                existingDonator.setAdditionalName(d.getAdditionalName());
                em.merge(existingDonator);
        }
    }
    @Override
    public void specialDeleteDonator(Donator d){
            d.specialDelete();
            em.merge(d);
    }
}

