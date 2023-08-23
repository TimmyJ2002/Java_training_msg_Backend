package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.exception.NotificationNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationDTO;
import de.msg.javatraining.donationmanager.persistence.model.Notification;
import de.msg.javatraining.donationmanager.service.NotificationService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/for_me")
    public ResponseEntity<?> getNotificationsByUserId(@NonNull HttpServletRequest request) throws ChangeSetPersister.NotFoundException {
        List<NotificationDTO> notificationDTOS = notificationService.getNotificationsByUserId(request);
        return ResponseEntity.ok(notificationDTOS);
    }

    @PostMapping("/save/{reciverId}")
    public ResponseEntity<?> createNotification(@PathVariable(name = "reciverId") Long recieverId, NotificationDTO notificationDTO){
        Notification notification = notificationService.createNotification(notificationDTO, recieverId);

        return new ResponseEntity<>(notification, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public Notification updateNotification(@PathVariable("id") Long notificationId,@RequestBody Notification notification) throws NotificationNotFoundException {
        return notificationService.updateNotification(notificationId, notification);
    }
}
