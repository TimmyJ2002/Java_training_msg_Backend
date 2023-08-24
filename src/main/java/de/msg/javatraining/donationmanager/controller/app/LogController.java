package de.msg.javatraining.donationmanager.controller.app;


import de.msg.javatraining.donationmanager.persistence.model.DTOs.LogDto;
import de.msg.javatraining.donationmanager.persistence.model.Log;
import de.msg.javatraining.donationmanager.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/log/create")
    public ResponseEntity<?> createLog(@RequestBody LogDto logRequest) {
        Log log = logService.createLog(logRequest.getAction(), logRequest.getSeverity(), logRequest.getMessage());

        return new ResponseEntity<>(log, HttpStatus.OK);
    }
}
