package de.msg.javatraining.donationmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends Throwable {
    public NotificationNotFoundException(String message) {
        super("Notification not Found" + message);
    }
}


