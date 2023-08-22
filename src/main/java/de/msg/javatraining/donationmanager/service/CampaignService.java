package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.exception.CampaignAlreadyExistsException;
import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.modelDTO.CampaignConverter;
import de.msg.javatraining.donationmanager.persistence.repository.impl.CampaignRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepositoryImpl campaignRepository;
    CampaignConverter campaignConverter;

    public List<Campaign> findAll() {
        List<Campaign> campaigns = campaignRepository.findAll();
        return campaigns;
    }

    public Campaign findById(Long id) {
        return campaignRepository.findById(id);
    }


    public Campaign create(Campaign campaign) throws IllegalArgumentException {
        validateCampaign(campaign);
        campaignRepository.create(campaign);
        return campaign;
    }

    public Campaign update(Long id, Campaign updateCampaign) {
        searchExistingName(id, updateCampaign);
        campaignRepository.update(id, updateCampaign);
        return updateCampaign;
    }

    public void delete(Long id) {
        existsDonations(findById(id));
        campaignRepository.delete(id);
    }


    private void validateCampaign(Campaign campaign) {
        Boolean isNameUnique = campaignRepository.existsByName(campaign.getName());
        if (isNameUnique) {
            throw new CampaignAlreadyExistsException("Name already exists.");
        }
    }

    private void existsDonations(Campaign campaign) {
        Boolean existDonation = campaignRepository.existsDonations(campaign.getDonationList());
        if (existDonation) {
            throw new CampaignAlreadyExistsException("Donations exists.");
        }
    }

    private void searchExistingName(Long id, Campaign campaign) {
        if(!campaign.getName().isEmpty()) {
            boolean isNameExisting = campaignRepository.existsByNameAndNotId(campaign.getName(), id);
            if (isNameExisting) {
                throw new CampaignAlreadyExistsException("Name already exists.");
            }
        }
    }
}
