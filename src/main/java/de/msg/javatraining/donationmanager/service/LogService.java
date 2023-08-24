package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Log;
import de.msg.javatraining.donationmanager.persistence.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    public Log createLog(String action, String severity, String message) {
        Log logEntry = new Log();
        logEntry.setAction(action);
        logEntry.setSeverity(severity);
        logEntry.setMessage(message);
        logRepository.save(logEntry);
        return logEntry;
    }
}
