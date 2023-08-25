package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDate;
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
    @Mock
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;
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
    @Test
    public void testFindById() {
        Donation expectedDonation = new Donation();
        expectedDonation.setId(1L);
        expectedDonation.setAmount(100);

        when(donationRepository.findByID(1)).thenReturn(expectedDonation);

        Donation result = donationService.findByID(1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100, result.getAmount());

        verify(donationRepository, times(1)).findByID(1);
    }

    @Test
    public void testFindByIdNotFound() {
        when(donationRepository.findByID(2)).thenReturn(null);

        Donation result = donationService.findByID(2);

        assertNull(result);

        verify(donationRepository, times(1)).findByID(2);
    }
    @Test
    public void testParseJwtWithToken() {
        String jwtToken = "Bearer testToken";
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn(jwtToken);

        String result = donationService.parseJwt(mockRequest);

        assertNotNull(result);
        assertEquals(jwtToken, result);
    }

    @Test
    public void testParseJwtWithoutToken() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn(null);

        String result = donationService.parseJwt(mockRequest);

        assertNull(result);
    }

    @Test
    public void testParseJwtWithEmptyToken() {
        String jwtToken = "Bearer ";
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn(jwtToken);

        String result = donationService.parseJwt(mockRequest);

        assertNotNull(result);
        assertEquals(jwtToken, result);
    }
    @Test
    public void testApproveDonation() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        String jwt = "mockJwtToken";
        String username = "mockUser";

        // Mocking user and JWT behavior
        User mockUser = new User();
        mockUser.setId(1L);
        when(jwtUtils.getUserNameFromJwtToken(jwt)).thenReturn(username);
        when(userService.findUserByUsername(username)).thenReturn(mockUser);

        // Mocking donation behavior
        Donation mockDonation = new Donation();
        mockDonation.setId(1L);
        when(donationRepository.findByID(1)).thenReturn(mockDonation);

        // Calling the method
        donationService.approveDonation(mockHttpServletRequest, 1L);

        // Verifying the behavior
        verify(donationRepository, times(1)).saveDonation(mockDonation);
        verify(mockDonation, times(1)).setApproved(true);
        verify(mockDonation, times(1)).setApprovedBy(mockUser);
        verify(mockDonation, times(1)).setApproveDate(LocalDate.now());
    }

    @Test
    public void testApproveDonation_DonationNotFound() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        String jwt = "mockJwtToken";
        String username = "mockUser";

        // Mocking user and JWT behavior
        User mockUser = new User();
        when(jwtUtils.getUserNameFromJwtToken(jwt)).thenReturn(username);
        when(userService.findUserByUsername(username)).thenReturn(mockUser);

        // Mocking donation behavior
        when(donationRepository.findByID(1)).thenReturn(null);

        // Calling the method and expecting an exception
        assertThrows(EntityNotFoundException.class, () -> donationService.approveDonation(mockHttpServletRequest, 1L));

        // Verifying that the saveDonation method was not called
        verify(donationRepository, never()).saveDonation(any(Donation.class));
    }
}
