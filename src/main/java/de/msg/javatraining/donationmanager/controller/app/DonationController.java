package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.*;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.NotificationDTO;
import de.msg.javatraining.donationmanager.persistence.model.DTOs.UserWithIdDTO;
import de.msg.javatraining.donationmanager.service.*;
import de.msg.javatraining.donationmanager.utils.DonationRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @Autowired
    NotificationService notificationService;

    @GetMapping("donations")
    public List<Donation> findAllDonations() {
        return donationService.findAll();
    }


    @PutMapping("donations/approve/{donation_id}")
    public ResponseEntity<?> approveDonation(
            @NonNull HttpServletRequest request,
            @PathVariable(name = "donation_id") Long donationId) throws Exception {

        donationService.approveDonation(request, donationId);

        List<UserWithIdDTO> users = userService.getAllUsers();

        for (UserWithIdDTO u : users){
            if (u.getRoles().stream().anyMatch(role ->
                    role.getName().equals(ERole.ROLE_MGN) || role.getName().equals(ERole.ROLE_CEN))){
                NotificationDTO notificationDTO = new NotificationDTO();

                notificationDTO.setTitle("Donation Approved");
                notificationDTO.setText("Donation with id: " + donationId + "was approved");
                notificationDTO.setCreatedDate(LocalDate.now());
                notificationDTO.setIsRead(false);

                notificationService.createNotification(notificationDTO, u.getUsername());
                System.out.println("Notification created");
            }
        }
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

    @PostMapping("/donations")
    public void deleteDonation(@RequestParam String id){
        donationService.deleteDonation(Long.parseLong(id));
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }

}
