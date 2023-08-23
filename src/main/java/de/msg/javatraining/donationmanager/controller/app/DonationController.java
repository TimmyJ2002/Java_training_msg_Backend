package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.CampaignService;
import de.msg.javatraining.donationmanager.service.DonationService;
import de.msg.javatraining.donationmanager.service.DonatorService;
import de.msg.javatraining.donationmanager.service.UserService;
import de.msg.javatraining.donationmanager.utils.DonationRequestWrapper;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Autowired
    DonatorService donatorService;

    @Autowired
    CampaignService campaignService;

    @GetMapping("donations")
    public List<Donation> findAllDonations() {
        return donationService.findAll();
    }


    @PutMapping("donations/approve/{donation_id}")
    public ResponseEntity<?> approveDonation(
            @NonNull HttpServletRequest request,
            @PathVariable(name = "donation_id") Long donationId) throws Exception {

        donationService.approveDonation(request, donationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("donations/addDonation")
    public ResponseEntity<?> addDonation(@NonNull HttpServletRequest request, @RequestBody DonationRequestWrapper donationRequestWrapper) {

        try {
            String jwt = parseJwt(request);
            String username = jwtUtils.getUserNameFromJwtToken(jwt);

            Donation donation = new Donation();
            donation.setAmount(donationRequestWrapper.getAmount());
            donation.setCurrency(donationRequestWrapper.getCurrency());
            Campaign campaign = campaignService.findById((long) donationRequestWrapper.getCampaignID());
            if (campaign == null) throw new IllegalArgumentException();
            donation.setCampaign(campaign);

            User createdByUser = userService.findUserByUsername(username);
            donation.setCreatedBy(createdByUser);
            Donator donator = donatorService.findById(donationRequestWrapper.getDonatorID());
            if (donator == null) throw new IllegalArgumentException();
            donation.setDonator(donator);

            donation.setCreatedDate(LocalDate.now());

            donation.setNotes(donationRequestWrapper.getNotes());
            donationService.addDonation(donation);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("donations/updateDonation")
    public ResponseEntity<?> updateDonation(@RequestBody DonationRequestWrapper donationRequestWrapper) {

        try {

            Campaign campaign = campaignService.findById((long) donationRequestWrapper.getCampaignID());
            Donator donator = donatorService.findById(donationRequestWrapper.getDonatorID());
            int donationID = donationRequestWrapper.getDonationID();

            Donation donation = donationService.findByID(donationID);

            if (campaign == null || donator == null || donation == null) throw new IllegalArgumentException();

            donationService.updateDonation(
                    donationRequestWrapper.getDonationID(),
                    donationRequestWrapper.getAmount(),
                    donationRequestWrapper.getCurrency(),
                    campaign,
                    donator,
                    donationRequestWrapper.getNotes());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }

}
