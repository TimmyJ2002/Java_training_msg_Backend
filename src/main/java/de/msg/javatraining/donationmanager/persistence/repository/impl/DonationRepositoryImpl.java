package de.msg.javatraining.donationmanager.persistence.repository.impl;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
@Repository
@Transactional
public class DonationRepositoryImpl implements DonationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveDonation(Donation donation) {
        em.persist(donation);
    }

    @Override
    public void deleteDonation(long id) {
        Donation d = (em.find(Donation.class, id));
        Query q = em.createNativeQuery("Delete from donation where id=?", Donation.class);
        q.setParameter(1,d.getId());
        q.executeUpdate();
    }

    @Override
    public List<Donation> findAll() {
        return em.createQuery("select dono from Donation dono", Donation.class).getResultList();
    }

    @Override
    public void updateDonation(int oldDonationID, int amount, String currency, Campaign campaign, Donator donator, String notes) {
        Donation oldDonation = em.find(Donation.class, oldDonationID);
        if (!oldDonation.isApproved()) {
            oldDonation.setAmount(amount);
            oldDonation.setCurrency(currency);
            oldDonation.setCampaign(campaign);
            oldDonation.setDonator(donator);
            oldDonation.setNotes(notes);
            em.merge(oldDonation);
        }
    }

    @Override
    public Donation findByID(long ID) {
        try {
            TypedQuery<Donation> query = em.createQuery(
                    "SELECT d FROM Donation d WHERE d.id = " + ID, Donation.class);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void approveDonation(Long donationId) {
        Donation donation = findByID(donationId);
        donation.setApproved(true);

        em.persist(donation);
    }


}
