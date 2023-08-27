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
        log.setUsername(logDto.getUsername());
        log.setTime(logDto.getTime());
        return log;
    }

    public LogDto logToDo(Log log) {
        LogDto logDto = new LogDto();
        logDto.setId(log.getId());
        logDto.setAction(log.getAction());
        logDto.setSeverity(log.getSeverity());
        logDto.setMessage(log.getMessage());
        logDto.setUsername(log.getUsername());
        logDto.setTime(log.getTime());
        return logDto;
    }
}
