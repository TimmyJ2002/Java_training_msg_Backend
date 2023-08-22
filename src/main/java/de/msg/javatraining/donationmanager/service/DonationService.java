package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.exception.DonationNotFoundException;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.DonationRepositoryJPA;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonationRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    DonationRepositoryImpl donationRepository;

    @Autowired
    DonationRepositoryJPA donationRepositoryJPA;

    @Autowired
    UserRepositoryInterface userRepository;

    @Autowired
    UserService userService;



    @Autowired
    JwtUtils jwtUtils;

    public List<Donation> findAll() {
        List<Donation> d = donationRepository.findAll();
        return d;
    }

    public Donation findById(Long id) {
        Optional<Donation> optionalDonation = donationRepositoryJPA.findById(id);

        if(optionalDonation.isPresent()){
            return optionalDonation.get();
        }
        else {
            throw new DonationNotFoundException("Donation not found with ID: " + id);
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