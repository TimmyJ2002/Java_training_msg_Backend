package de.msg.javatraining.donationmanager.controller.app;


import de.msg.javatraining.donationmanager.persistence.model.DTOs.LogConverter;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.LogDto;
import de.msg.javatraining.donationmanager.persistence.model.Log;
import de.msg.javatraining.donationmanager.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private LogConverter logConverter;

    @PostMapping("/log/create")
    @ResponseBody
    public ResponseEntity<?> createLog(@RequestBody LogDto logRequest) {
        try {
            Log createdLog = logService.create(logConverter.dtoToLog(logRequest));
            return new ResponseEntity<>(createdLog, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Could not be added.", HttpStatus.BAD_REQUEST);
        }
    }
}
