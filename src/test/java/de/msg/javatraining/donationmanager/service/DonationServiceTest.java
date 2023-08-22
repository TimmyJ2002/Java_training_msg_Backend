package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

    // Add more test methods for other service methods if needed
}
