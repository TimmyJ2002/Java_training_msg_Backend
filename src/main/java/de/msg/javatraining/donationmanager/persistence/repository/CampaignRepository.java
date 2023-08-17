package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository {
    Campaign create(Campaign campaign);
    void delete(Campaign campaign);
    Campaign update(Long id, Campaign campaign);
    List<Campaign> findAll();
    Campaign findById(long id);
    Campaign findByName(String name);
}
