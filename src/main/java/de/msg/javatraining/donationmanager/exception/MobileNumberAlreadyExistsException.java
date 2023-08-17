package de.msg.javatraining.donationmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MobileNumberAlreadyExistsException extends RuntimeException {
    public MobileNumberAlreadyExistsException(String message) {
        super(message);
    }
}

