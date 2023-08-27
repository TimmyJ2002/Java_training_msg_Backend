package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.exception.CampaignAlreadyExistsException;
import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.repository.impl.CampaignRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CampaignServiceTest {

    @Mock
    private CampaignRepositoryImpl campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateValidCampaign() {
        Campaign campaign = new Campaign();
        campaign.setName("New Campaign");

        when(campaignRepository.existsByName(anyString())).thenReturn(false);
        when(campaignRepository.create(any(Campaign.class))).thenReturn(campaign);

        Campaign createdCampaign = campaignService.create(campaign);

        assertNotNull(createdCampaign);
        assertEquals("New Campaign", createdCampaign.getName());

        verify(campaignRepository, times(1)).existsByName("New Campaign");
        verify(campaignRepository, times(1)).create(campaign);
    }

    @Test
    public void testCreateCampaignWithExistingName() {
        Campaign campaign = new Campaign();
        campaign.setName("Existing Campaign");

        when(campaignRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(CampaignAlreadyExistsException.class, () -> campaignService.create(campaign));

        verify(campaignRepository, times(1)).existsByName("Existing Campaign");
        verify(campaignRepository, never()).create(any(Campaign.class));
    }

    @Test
    public void testFindAll() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignList.add(new Campaign(1L,"Campaign name 1", "Campaign 1"));
        campaignList.add(new Campaign(2L, "Campaign name 2","Campaign 2"));

        when(campaignRepository.findAll()).thenReturn(campaignList);

        List<Campaign> result = campaignService.findAll();

        assertEquals(2, result.size());
        assertEquals("Campaign 1", result.get(0).getName());
        assertEquals("Campaign 2", result.get(1).getName());

        verify(campaignRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Campaign campaign = new Campaign(1L,"Campaign name 1", "Test Campaign");

        when(campaignRepository.findById(1L)).thenReturn(campaign);

        Campaign result = campaignService.findById(1L);

        assertNotNull(result);
        assertEquals("Test Campaign", result.getName());

        verify(campaignRepository, times(1)).findById(1L);
    }
    @Test
    public void testUpdate() {
        Campaign existingCampaign = new Campaign(1L,"Campaign name 1", "Existing Campaign");
        Campaign updatedCampaign = new Campaign(1L, "Campaign name 2","Updated Campaign");

        when(campaignRepository.existsByNameAndNotId(anyString(), anyLong())).thenReturn(false);
        when(campaignRepository.findById(1L)).thenReturn(existingCampaign);

        Campaign result = campaignService.update(1L, updatedCampaign);

        assertNotNull(result);
        assertEquals("Updated Campaign", result.getName());

        verify(campaignRepository, times(1)).existsByNameAndNotId("Updated Campaign", 1L);
        verify(campaignRepository, times(1)).findById(1L);
        verify(campaignRepository, times(1)).update(1L, updatedCampaign);
    }

    @Test
    public void testDeleteWithDonations() {
        Campaign campaignWithDonations = new Campaign(1L, "Campaign name 2","Campaign with Donations");

        when(campaignRepository.findById(1L)).thenReturn(campaignWithDonations);
        when(campaignRepository.existsDonations(campaignWithDonations.getDonationList())).thenReturn(true);

        assertThrows(CampaignAlreadyExistsException.class, () -> campaignService.delete(1L));

        verify(campaignRepository, times(1)).findById(1L);
        verify(campaignRepository, never()).delete(anyLong());
    }

    @Test
    public void testDeleteWithoutDonations() {
        Campaign campaignWithoutDonations = new Campaign(2L,"Campaign name 2", "Campaign without Donations");

        when(campaignRepository.findById(2L)).thenReturn(campaignWithoutDonations);
        when(campaignRepository.existsDonations(campaignWithoutDonations.getDonationList())).thenReturn(false);

        campaignService.delete(2L);

        verify(campaignRepository, times(1)).findById(2L);
        verify(campaignRepository, times(1)).delete(2L);
    }


    // You can write similar tests for other methods like update and delete

    // Remember to test other methods and scenarios as well.

}

