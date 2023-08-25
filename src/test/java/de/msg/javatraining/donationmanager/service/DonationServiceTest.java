package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DonationServiceTest {

    @Mock
    private DonationRepositoryImpl donationRepository;

    @InjectMocks
    private DonationService donationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Donation> donations = new ArrayList<>();
        donations.add(new Donation());
        donations.add(new Donation());

        when(donationRepository.findAll()).thenReturn(donations);

        List<Donation> result = donationService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testAddDonation() {
        Donation donation = new Donation();
        donation.setAmount(100);
        donation.setCurrency("USD");

        donationService.addDonation(donation);

        verify(donationRepository, times(1)).saveDonation(donation);
    }

    @Test
    public void testDeleteDonation() {
        long donationId = 1L;

        donationService.deleteDonation(donationId);

        verify(donationRepository, times(1)).deleteDonation(donationId);
    }


    @Test
    public void testUpdateDonation() {
        int oldDonationID = 1;
        int newAmount = 200;
        String newCurrency = "EUR";
        Campaign newCampaign = new Campaign();
        Donator newDonator = new Donator();
        String newNotes = "Updated notes";

        donationService.updateDonation(oldDonationID, newAmount, newCurrency, newCampaign, newDonator, newNotes);

        verify(donationRepository, times(1)).updateDonation(oldDonationID, newAmount, newCurrency, newCampaign, newDonator, newNotes);
    }
//    @Test
//    public void testApproveDonation() throws ChangeSetPersister.NotFoundException {
//        User approvedByUser = new User();
//        approvedByUser.setUsername("testUser");
//
//        Donation donation = new Donation();
//        donation.setId(1L);
//
//        String jwtToken = "testJwtToken";
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//
//        when(jwtUtils.getUserNameFromJwtToken(jwtToken)).thenReturn("testUser");
//        when(userService.findUserByUsername("testUser")).thenReturn(approvedByUser);
//        when(donationService.findByID(1)).thenReturn(donation);
//
//        donationService.approveDonation(mockRequest, 1L);
//
//        assertTrue(donation.isApproved());
//        assertEquals(approvedByUser, donation.getApprovedBy());
//        assertNotNull(donation.getApproveDate());
//
//        verify(donationRepositoryJPA, times(1)).save(donation);
//    }
//
//    @Test
//    public void testApproveDonationUserNotFound() throws ChangeSetPersister.NotFoundException {
//        String jwtToken = "testJwtToken";
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//
//        when(jwtUtils.getUserNameFromJwtToken(jwtToken)).thenReturn("testUser");
//        when(userService.findUserByUsername("testUser")).thenReturn(null);
//
//        assertThrows(ChangeSetPersister.NotFoundException.class,
//                () -> donationService.approveDonation(mockRequest, 1L));
//    }
//
//    @Test
//    public void testApproveDonationDonationNotFound() throws ChangeSetPersister.NotFoundException {
//        User approvedByUser = new User();
//        approvedByUser.setUsername("testUser");
//
//        String jwtToken = "testJwtToken";
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//
//        when(jwtUtils.getUserNameFromJwtToken(jwtToken)).thenReturn("testUser");
//        when(userService.findUserByUsername("testUser")).thenReturn(approvedByUser);
//        when(donationService.findByID(1)).thenReturn(null);
//
//        assertThrows(ChangeSetPersister.NotFoundException.class,
//                () -> donationService.approveDonation(mockRequest, 1L));
//    }
}
