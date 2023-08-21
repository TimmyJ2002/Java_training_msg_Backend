package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.exception.InvalidDataException;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonatorRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DonatorServiceTest {

    @Mock
    private DonatorRepositoryImpl donatorRepository;

    @MockBean
    private EntityManager em;

    @Spy
    private DonatorService donatorServiceSpy;

    @InjectMocks
    private DonatorService donatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Donator> donators = new ArrayList<>();
        donators.add(new Donator());
        donators.add(new Donator());

        when(donatorRepository.findAll()).thenReturn(donators);

        List<Donator> result = donatorService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        long id = 1;
        Donator donator = new Donator();
        donator.setId(id);

        when(donatorRepository.findByID(id)).thenReturn(donator);

        Donator result = donatorService.findById(id);

        assertEquals(id, result.getId());
    }

    @Test
    public void testSaveDonator_ValidData() throws InvalidDataException {
        Donator validDonator = new Donator();
        // Set validDonator properties
        validDonator.setFirstName("John");
        validDonator.setLastName("Doe");
        validDonator.setId(1L);
        validDonator.setActive(true);

        // Mock repository methods
        doNothing().when(donatorRepository).saveDonator(validDonator);

        // Call the method
        donatorService.saveDonator(validDonator);

        // Verify that repository methods were called
        verify(donatorRepository, times(1)).saveDonator(validDonator);
    }

    @Test
    public void testSaveDonator_InvalidData() {
        Donator invalidDonator = new Donator();
        // Set invalidDonator properties
        invalidDonator.setFirstName("John");
        invalidDonator.setLastName("");
        invalidDonator.setId(1L);
        invalidDonator.setActive(true);

        // Mock repository methods
        doThrow(InvalidDataException.class).when(donatorRepository).saveDonator(invalidDonator);

        // Call the method
        assertThrows(InvalidDataException.class, () -> donatorService.saveDonator(invalidDonator));

        // Verify that repository methods were called
        verify(donatorRepository, never()).saveDonator(invalidDonator);
    }

    @Test
    public void testSaveDonator_UnexpectedRollbackException() {
        Donator validDonator = new Donator();
        // Set validDonator properties
        validDonator.setFirstName("John");
        validDonator.setLastName("Doe");
        validDonator.setId(1L);
        validDonator.setActive(true);

        // Mock repository methods
        doThrow(UnexpectedRollbackException.class).when(donatorRepository).saveDonator(validDonator);

        // Call the method
        donatorService.saveDonator(validDonator);

        // Verify that repository methods were called
        verify(donatorRepository, times(1)).saveDonator(validDonator);
    }

    @Test
    public void testEditDonator() {
        long id = 1L;
        Donator existingDonator = new Donator();
        existingDonator.setId(id);

        Donator newDonatorInfo = new Donator();
        newDonatorInfo.setFirstName("NewFirstName");
        newDonatorInfo.setLastName("NewLastName");

        // Mock repository methods
        when(donatorRepository.existsById(id)).thenReturn(true);

        // Use doNothing() for void methods
        doNothing().when(donatorRepository).editDonator(id, newDonatorInfo);

        donatorService.editDonator(id, newDonatorInfo);

        // Verify that repository methods were called
        verify(donatorRepository, times(1)).existsById(id);
        verify(donatorRepository, times(1)).editDonator(id, newDonatorInfo);
    }

    @Test
    public void testEditDonator_InvalidDataException() {
        long id = 1L;
        Donator invalidDonator = new Donator();
        invalidDonator.setId(1L);
        invalidDonator.setFirstName("John");
        invalidDonator.setLastName("");
        // Mock repository methods
        // Mock the validateDonator method using the spy
        doThrow(InvalidDataException.class).when(donatorServiceSpy).validateDonator(invalidDonator);

        // Call the method
        assertThrows(InvalidDataException.class, () -> donatorServiceSpy.editDonator(id,invalidDonator));

        // Verify that validateDonator method was called
        verify(donatorServiceSpy, times(1)).validateDonator(invalidDonator);

        // Verify that repository methods were not called
        verify(donatorRepository, never()).saveDonator(invalidDonator);
    }

    @Test
    public void testEditDonator_NullPointerException() {
        long id = 1L;
        Donator newDonatorInfo = new Donator();
        newDonatorInfo.setId(2L);
        newDonatorInfo.setFirstName("John");
        newDonatorInfo.setLastName("Doe");
        // Mock repository methods
        when(donatorRepository.existsById(id)).thenReturn(false);

        // Verify that NullPointerException is thrown
        assertThrows(NullPointerException.class, () -> donatorService.editDonator(id, newDonatorInfo));

        // Verify that repository methods were called
        verify(donatorRepository, times(1)).existsById(id);
        verify(donatorRepository, never()).editDonator(id, newDonatorInfo);
    }

    @Test
    public void testSpecialDeleteDonator() {
        Donator donatorToDelete = new Donator();
        // Set properties of donatorToDelete
        donatorToDelete.setFirstName("John");
        donatorToDelete.setLastName("Doe");
        // Call the method
        donatorService.specialDeleteDonator(donatorToDelete);

        // Verify that the repository method was called
        verify(donatorRepository, times(1)).specialDeleteDonator(donatorToDelete);
    }
}
