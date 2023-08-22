package de.msg.javatraining.donationmanager.persistence.repository.impl;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class DonationRepositoryImpl implements DonationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Donation> findAll() {
        return em.createQuery("SELECT d FROM Donation d", Donation.class).getResultList();
    }

    @Override
    public Donation findById(long id) {
        return em.find(Donation.class, id);
    }

    @Override
    public void deleteDonation(Donation d) {
        em.remove(d);
    }
}
