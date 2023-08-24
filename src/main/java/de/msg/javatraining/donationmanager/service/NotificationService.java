package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.exception.NotificationNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationDTO;
import de.msg.javatraining.donationmanager.persistence.model.Notification;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.NotificationRepositoryInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationMapper.mapNotificationDTOToNotification;
import static de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationMapper.mapNotificationToNotificationDTO;

@Service
@EnableScheduling
public class NotificationService {

    @Autowired
    private NotificationRepositoryInterface notificationRepository;

    @Autowired
    private JwtUtils jwtUtils;

//    @Autowired
//    private UserService userService;

    public List<NotificationDTO>  getNotificationsByUserId(HttpServletRequest request) throws ChangeSetPersister.NotFoundException {
        String jwt = parseJwt(request);
        String userName = jwtUtils.getUserNameFromJwtToken(jwt);

        // BEFORE || After getting user |>
        List<Notification> notifications = notificationRepository.findByNotificationReceiverUsername(userName);

//        LocalDate hardcodedDate = LocalDate.of(2023, 8, 22);
//        NotificationDTO test = new NotificationDTO(30, "TEST", hardcodedDate, false);
//        createNotification(test, );

        return notifications.stream()
               .map(notification -> mapNotificationToNotificationDTO(notification))
                .collect(Collectors.toList());
    }

    public Notification createNotification(NotificationDTO notificationDTO, String username) {

        Notification notification = mapNotificationDTOToNotification(notificationDTO);
        notification.setNotificationReceiverUsername(username);

        return notificationRepository.save(notification);
    }

    public void createAccountDeactivatedNotification(String username) {
        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setTitle("Account Deactivated");
        notificationDTO.setText("Account was deactivated due to incorrect password entered 5 times");
        notificationDTO.setCreatedDate(LocalDate.now());
        notificationDTO.setIsRead(false);

        this.createNotification(notificationDTO, username);

    }

    @Scheduled(cron = "0 01 11 * * ?") //at midnight everyday
    public void deleteOldNotifications() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<Notification> notificationsToDelete = notificationRepository.findByCreatedDateBefore(thirtyDaysAgo);
        for (Notification notification : notificationsToDelete) {
            notificationRepository.delete(notification);
        }
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }

    public Notification updateNotification(Long notificationId, Notification notification) throws NotificationNotFoundException{
        Optional<Notification> notificationToFind = notificationRepository.findById(notificationId);
        if (notificationToFind.isEmpty()) {
            throw new NotificationNotFoundException("Notification with ID " + notificationId + " not found");
        }
        Notification notificationToEdit = notificationToFind.get();

        notificationToEdit.setId(notificationToFind.get().getId());
        if (notification.getTitle() != null) {
            notificationToEdit.setTitle(notification.getTitle());
        }
        if (notification.getText() != null){
            notificationToEdit.setText(notification.getText());
        }
        if (notification.getIsRead() != null){
            notificationToEdit.setRead(notification.getIsRead());
        }
        return notificationRepository.save(notificationToEdit);
    }
}
