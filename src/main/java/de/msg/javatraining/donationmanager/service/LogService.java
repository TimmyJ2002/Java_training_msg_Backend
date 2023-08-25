package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Log;
import de.msg.javatraining.donationmanager.persistence.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    public Log create(Log log) {
        Log logEntry = new Log();
        logEntry.setAction(log.getAction());
        logEntry.setSeverity(log.getSeverity());
        logEntry.setMessage(log.getMessage());
        logRepository.save(logEntry);
        return logEntry;
    }

}
