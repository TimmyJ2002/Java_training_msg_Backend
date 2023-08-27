package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Log;
import de.msg.javatraining.donationmanager.persistence.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    public Log create(String action, String severity, String message, String username, LocalDateTime time) {
        Log logEntry = new Log();
        logEntry.setAction(action);
        logEntry.setSeverity(severity);
        logEntry.setMessage(message);
        logEntry.setUsername(username);
        logEntry.setTime(time);
        logRepository.save(logEntry);
        return logEntry;
    }

}
