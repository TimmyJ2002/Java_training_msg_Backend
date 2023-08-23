package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository{
    Campaign create(Campaign campaign);
    Object delete(Long id);
    Campaign update(Long id, Campaign campaign);
    List<Campaign> findAll();
    Campaign findById(long id);
    Campaign findByName(String name);
    Boolean existsByName(String name);
    Boolean existsDonations(List<Donation> donationList);
    Boolean existsByNameAndNotId(String name, Long id);
}
