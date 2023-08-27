package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.modelDTO.CampaignDto;
import de.msg.javatraining.donationmanager.persistence.modelDTO.CampaignConverter;
import de.msg.javatraining.donationmanager.service.CampaignService;
import de.msg.javatraining.donationmanager.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private LogService logService;

    @Autowired
    JwtUtils jwtUtils;

    private final CampaignConverter campaignConverter = new CampaignConverter();

    @GetMapping("/campaign")
    public List<CampaignDto> findAll() {
        List<Campaign> campaigns = campaignService.findAll();
        return campaigns.stream()
                .map(campaignConverter::campaignToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/campaign/{id}")
    public Campaign findById(@PathVariable Long id) {
        return campaignService.findById(id);
    }

    @PostMapping("/campaign/create")
    @ResponseBody
    public ResponseEntity<?> createCampaign(@NonNull HttpServletRequest request, @RequestBody CampaignDto campaign) {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        try {
            Campaign createdCampaign = campaignService.create(campaignConverter.dtoToCampaign(campaign));
            logService.create("create", "info", "Campaign created", username, LocalDateTime.now());
            return new ResponseEntity<>(createdCampaign, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logService.create("create", "error", "Campaign could not be created", username, LocalDateTime.now());
            return new ResponseEntity<>("Campaign name already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/campaign/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateCampaign(@NonNull HttpServletRequest request, @PathVariable Long id, @RequestBody CampaignDto updateCampaign) {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        try {
            logService.create("modify", "info", "Campaign modified", username, LocalDateTime.now());
            Campaign updatedCampaign = campaignService.update(id, campaignConverter.dtoToCampaign(updateCampaign));
            return new ResponseEntity<>(updatedCampaign, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Campaign name already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/campaign/delete/{id}")
    public ResponseEntity<?> deleteCampaign(@NonNull HttpServletRequest request, @PathVariable Long id) {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        try {
            logService.create("delete", "info", "Campaign deleted", username, LocalDateTime.now());
            campaignService.delete(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity("Campaign could not be deleted because it has approved donations.", HttpStatus.BAD_REQUEST);
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
