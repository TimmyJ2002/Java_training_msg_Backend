package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.exception.DonationNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepository;
import jakarta.persistence.NoResultException;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepositoryJPA;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}

//    public void approveDonation(HttpServletRequest request, Donation donation) {
//
//        String jwt = parseJwt(request);
//        String username = jwtUtils.getUserNameFromJwtToken(jwt);
//
//        Optional<User> createdByUser = userService.findUserByUsername(username);
//
//       donation.setApproved(true);
//       donation.setApprovedBy(createdByUser);
//       donation.setApproveDate(LocalDate.now());
//       donationRepositoryJPA.save(donation);
//    }

    public void approveDonation(HttpServletRequest request, Long donationId) throws Exception {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        User approvedByUser = userService.findUserByUsername(username)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Donation donation = findById(donationId);

        if (Objects.equals(approvedByUser.getId(), donation.getCreatedBy().getId())){
            throw new Exception("Donations can't be approved by the user who created them");
        }
        if (donation != null) {
            donation.setApproved(true);
            donation.setApprovedBy(approvedByUser);
            donation.setApproveDate(LocalDate.now());
            donationRepositoryJPA.save(donation);
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