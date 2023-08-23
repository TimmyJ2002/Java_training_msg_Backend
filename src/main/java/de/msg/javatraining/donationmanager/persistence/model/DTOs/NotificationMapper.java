package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.Notification;

public class NotificationMapper {

    public static Notification mapNotificationDTOToNotification(NotificationDTO notificationDTO){
        Notification notification = new Notification();

        notification.setId(notificationDTO.getId());
        notification.setTitle(notificationDTO.getTitle());
        notification.setText(notificationDTO.getText());
        notification.setCreatedDate(notificationDTO.getCreatedDate());

        boolean primitiveRead = false;
        if (notificationDTO.getIsRead()!= null){
            primitiveRead = notificationDTO.getIsRead();

        }
        notification.setRead(primitiveRead);


        return notification;
    }


    public static NotificationDTO mapNotificationToNotificationDTO(Notification notification){
        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setId(notification.getId());
        notificationDTO.setTitle(notification.getTitle());
        notificationDTO.setText(notification.getText());
        notificationDTO.setCreatedDate(notification.getCreatedDate());
        notificationDTO.setIsRead(notification.isRead());


        return notificationDTO;
    }
}
