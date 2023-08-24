package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepository;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepositoryInterface;

    @Autowired
    UserRepositoryInterface userRepository;

    @Autowired
    UserService userService;



    @Autowired
    JwtUtils jwtUtils;

    public void addDonation(Donation donation) {
        donationRepositoryInterface.saveDonation(donation);
    }

    public void removeDonation(Donation donation) {
        donationRepositoryInterface.deleteDonation(donation);
    }

    public List<Donation> findAll() {
        return donationRepositoryInterface.findAll();
    }

    public void updateDonation(int oldDonationID, int amount, String currency, Campaign campaign, Donator donator, String notes) {
        donationRepositoryInterface.updateDonation(oldDonationID, amount, currency, campaign, donator, notes);
    }

    public Donation findByID(int ID) {
            return donationRepositoryInterface.findByID(ID);
    }

    public void approveDonation(HttpServletRequest request, Long donationId) throws Exception {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        User approvedByUser = userService.findUserByUsername(username);
        if (approvedByUser == null) throw new ChangeSetPersister.NotFoundException();
        Donation donation = findByID(Math.toIntExact(donationId));

        if (donation != null) {
            if (Objects.equals(approvedByUser.getId(), donation.getCreatedBy().getId())) {
                throw new Exception("Donations can't be approved by the user who created them");
            }

            donation.setApproved(true);
            donation.setApprovedBy(approvedByUser);
            donation.setApproveDate(LocalDate.now());
            donationRepositoryInterface.saveDonation(donation);
        } else {
            // Handle case where the donation is not found
            throw new ChangeSetPersister.NotFoundException();
        }
    }



    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }

}