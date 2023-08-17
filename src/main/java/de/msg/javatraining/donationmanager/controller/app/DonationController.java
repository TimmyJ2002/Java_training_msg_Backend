package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.service.DonationService;
import de.msg.javatraining.donationmanager.service.UserDetailsServiceImpl;
import de.msg.javatraining.donationmanager.service.UserService;
import de.msg.javatraining.donationmanager.utils.DonationRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DonationController {

    @Autowired
    DonationService donationService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("donations")
    public List<Donation> findAllDonations() {
        return donationService.findAll();
    }

    @PostMapping("donations/addDonation")
    public void addDonation(@NonNull HttpServletRequest request, DonationRequestWrapper donationRequestWrapper) {

        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        Donation donation = new Donation();
        donation.setAmount(donationRequestWrapper.getAmount());
        donation.setCurrency(donationRequestWrapper.getCurrency());
        donation.setCampaign(donationRequestWrapper.getCampaign());

        User createdByUser = userService.findUserByUsername(username);
        donation.setCreatedBy(createdByUser);
        donation.setDonator(donationRequestWrapper.getDonator());

        donation.setNotes(donationRequestWrapper.getNotes());
        donationService.addDonation(donation);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }

}
