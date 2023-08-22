package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.exception.InvalidDataException;
import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.modelDTO.DonatorDTO;
import de.msg.javatraining.donationmanager.persistence.modelDTO.DonatorMapper;
import de.msg.javatraining.donationmanager.persistence.repository.DonatorRepository;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonatorRepositoryImpl;
import de.msg.javatraining.donationmanager.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class DonatorController {
    class IdWrapper{
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @Autowired
    private DonatorService donatorService;

    @Autowired
    private DonatorRepository donatorRepository;

    private final DonatorMapper donatorMapper = new DonatorMapper();
    @GetMapping("/donator/edit")
    public List<DonatorDTO> findAll() {
        List<Donator> donators = donatorService.findAll();
        return donators.stream()
                .map(donatorMapper::donatorToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/donator/edit/{id}")
    public DonatorDTO findbyId(@PathVariable  long id) {
        return donatorMapper.donatorToDto(donatorService.findById(id));
    }


    @PostMapping("/donator/edit/{id}")
    @ResponseBody
    public ResponseEntity<?> editDonator(@PathVariable  int id, @RequestBody DonatorDTO c){
        try {
            donatorService.editDonator(id,donatorMapper.dtoToDonator(c));
            return new ResponseEntity<>(c, HttpStatus.OK);
        }
        catch (InvalidDataException e ) {
            return new ResponseEntity<>("Donator mandatory fields are empty", HttpStatus.BAD_REQUEST);
        }
        catch (NullPointerException e){
            return new ResponseEntity<>("Id does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/donator/create")
    @ResponseBody
    public ResponseEntity<?> createDonator(@RequestBody DonatorDTO c){
        try {
            donatorService.saveDonator(donatorMapper.dtoToDonator(c));
            return new ResponseEntity<>(c, HttpStatus.OK);
        }
        catch (InvalidDataException e) {
            return new ResponseEntity<>("Donator mandatory fields are empty", HttpStatus.BAD_REQUEST);

        }
    }
    @PostMapping("/donator/delete")
    @ResponseBody
    public ResponseEntity<?> deleteDonator(@RequestBody DonatorDTO c){
        try {
            Donator d = donatorMapper.dtoToDonator(c);
            d = donatorService.findById(d.getId());
            donatorService.specialDeleteDonator(d);
            return new ResponseEntity<>(c, HttpStatus.OK);
        }
        catch (NullPointerException e){
            return new ResponseEntity<>("Id does not exist", HttpStatus.BAD_REQUEST);
        }
    }
}
