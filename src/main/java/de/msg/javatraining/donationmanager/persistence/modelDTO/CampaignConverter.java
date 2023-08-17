package de.msg.javatraining.donationmanager.persistence.modelDTO;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.modelDTO.CampaignDto;
import org.springframework.stereotype.Component;

@Component
public class CampaignConverter {

    public CampaignDto campaignToDto(Campaign campaign) {
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setId(campaign.getId());
        campaignDto.setName(campaign.getName());
        campaignDto.setPurpose(campaign.getPurpose());
        return campaignDto;
    }

    public Campaign dtoToCampaign(CampaignDto campaignDto) {
        Campaign campaign = new Campaign();
        campaign.setId(campaignDto.getId());
        campaign.setName(campaignDto.getName());
        campaign.setPurpose(campaignDto.getPurpose());
        return campaign;
    }
}
