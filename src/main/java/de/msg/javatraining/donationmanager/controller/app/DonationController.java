package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.DonationService;
import de.msg.javatraining.donationmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class DonationController {

    @Autowired
    DonationService donationService;

    @Autowired
    UserService userService;

    @GetMapping("/donations")
    public List<Donation> getAllDonations() {
        return donationService.findAll();
    }


    @PutMapping("/approve/{donation_id}/{approvedby_id}")
    public Donation approveDonation(@PathVariable(name = "donation_id") Long donationId, @PathVariable(name = "approvedby_id") Long aprrovedById) {
        Donation donation = donationService.findById(donationId);
        User approvedBy = userService.findById(aprrovedById);

        if (!Objects.equals(donation.getCreatedBy().getId(), aprrovedById)){
            donationService.approveDonation(donation, approvedBy);
            return donation;
        }
        else {
            return null;
        }
    }
}


