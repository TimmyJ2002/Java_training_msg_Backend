package de.msg.javatraining.donationmanager.service;
import de.msg.javatraining.donationmanager.exception.NotificationNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationDTO;
import de.msg.javatraining.donationmanager.persistence.model.Notification;
import de.msg.javatraining.donationmanager.persistence.repository.NotificationRepositoryInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableScheduling
public class NotificationServiceTest {

    @MockBean
    private NotificationRepositoryInterface notificationRepository;

    @Mock
    private HttpServletRequest mockRequest;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testGetNotificationsByUserId() throws ChangeSetPersister.NotFoundException {
//        String jwtToken = "Bearer testToken";
//        String username = "admin";
//
//        when(mockRequest.getHeader("Authorization")).thenReturn(jwtToken);
//        when(notificationRepository.findByNotificationReceiverUsername(username)).thenReturn(new ArrayList<>());
//
//        List<NotificationDTO> result = notificationService.getNotificationsByUserId(mockRequest);
//
//        assertNotNull(result);
//        assertEquals(0, result.size());
//    }

    @Test
    public void testCreateNotification() {
        String username = "testUser";
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTitle("Test Title");

        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        Notification result = notificationService.createNotification(notificationDTO, username);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        assertEquals(username, result.getNotificationReceiverUsername());
    }

    @Test
    public void testDeleteOldNotifications() {
        Notification notification1 = new Notification();
        notification1.setCreatedDate(LocalDate.now().minusDays(31));
        Notification notification2 = new Notification();
        notification2.setCreatedDate(LocalDate.now().minusDays(29));

        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);

        when(notificationRepository.findByCreatedDateBefore(any(LocalDate.class))).thenReturn(notifications);

        notificationService.deleteOldNotifications();

        verify(notificationRepository, times(1)).delete(notification1);
        verify(notificationRepository, never()).delete(notification2);
    }

    @Test
    public void testParseJwt() {
        String jwtToken = "Bearer testToken";

        when(mockRequest.getHeader("Authorization")).thenReturn(jwtToken);

        String result = notificationService.parseJwt(mockRequest);

        assertNotNull(result);
        assertEquals(jwtToken, result);
    }

    @Test
    public void testUpdateNotification() throws NotificationNotFoundException {
        Long notificationId = 1L;
        Notification notificationToUpdate = new Notification();
        notificationToUpdate.setTitle("Updated Title");
        notificationToUpdate.setId(notificationId);

        Optional<Notification> optionalNotification = Optional.of(new Notification());
        when(notificationRepository.findById(notificationId)).thenReturn(optionalNotification);
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        //notificationRepository.save(notificationToUpdate);
        Notification result = notificationService.updateNotification(notificationId, notificationToUpdate);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    public void testUpdateNotificationNotFound() {
        Long notificationId = 1L;
        Notification notificationToUpdate = new Notification();
        notificationToUpdate.setTitle("Updated Title");

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        assertThrows(NotificationNotFoundException.class,
                () -> notificationService.updateNotification(notificationId, notificationToUpdate));
    }

}
