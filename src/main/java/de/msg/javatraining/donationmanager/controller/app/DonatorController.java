package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.persistence.model.Donation;
import de.msg.javatraining.donationmanager.persistence.model.Donator;
import de.msg.javatraining.donationmanager.persistence.modelDTO.DonatorDTO;
import de.msg.javatraining.donationmanager.persistence.modelDTO.DonatorMapper;
import de.msg.javatraining.donationmanager.persistence.repository.DonatorRepository;
import de.msg.javatraining.donationmanager.persistence.repository.impl.DonatorRepositoryImpl;
import de.msg.javatraining.donationmanager.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void editDonator(@PathVariable  int id, @RequestBody DonatorDTO c){
        donatorService.editDonator(id,donatorMapper.dtoToDonator(c));
    }

    @PostMapping("/donator/create")
    public void createDonator(@RequestBody DonatorDTO c){
        donatorService.saveDonator(donatorMapper.dtoToDonator(c));
    }

    @PostMapping("/donator/delete")
    public void deleteDonator(@RequestBody DonatorDTO c){
        Donator d = donatorMapper.dtoToDonator(c);
        d = donatorService.findById(d.getId());
        System.out.println(c.getId() + " " + c.getLastName() + " " + c.getFirstName());
        donatorService.specialDeleteDonator(d);
    }
}
