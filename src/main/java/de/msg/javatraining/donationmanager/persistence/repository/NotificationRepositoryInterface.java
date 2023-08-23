package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepositoryInterface extends JpaRepository<Notification,Long> {

    List<Notification> findByNotificationRecieverId(Long userId);

    List<Notification> findByCreatedDateBefore(LocalDate thirtyDaysAgo);
}

