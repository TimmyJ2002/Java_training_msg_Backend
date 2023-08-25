package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.Log;
import org.springframework.stereotype.Component;

@Component
public class LogConverter {

    public Log dtoToLog(LogDto logDto) {
        Log log = new Log();
        log.setId(logDto.getId());
        log.setAction(logDto.getAction());
        log.setSeverity(logDto.getSeverity());
        log.setMessage(logDto.getMessage());
        return log;
    }
}
