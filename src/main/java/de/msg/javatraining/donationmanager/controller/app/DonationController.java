package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.DonationService;
import de.msg.javatraining.donationmanager.service.UserDetailsImpl;
import de.msg.javatraining.donationmanager.service.UserService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class DonationController {

    @Autowired
    DonationService donationService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/donations")
    public List<Donation> getAllDonations() {
        return donationService.findAll();
    }


    @PutMapping("donations/approve/{donation_id}")
    public ResponseEntity<?> approveDonation(
            @NonNull HttpServletRequest request,
            @PathVariable(name = "donation_id") Long donationId) throws ChangeSetPersister.NotFoundException {

        donationService.approveDonation(request, donationId);
        return ResponseEntity.ok().build();
    }

}


