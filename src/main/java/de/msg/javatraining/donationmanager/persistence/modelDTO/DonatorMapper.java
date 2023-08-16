package de.msg.javatraining.donationmanager.persistence.modelDTO;


import de.msg.javatraining.donationmanager.persistence.model.Donator;
import org.springframework.stereotype.Component;

@Component
public class DonatorMapper {

    public Donator dtoToDonator(DonatorDTO dto) {
        Donator u = new Donator();
        u.setId(dto.getId());
        u.setLastName(dto.getLastName());
        u.setFirstName(dto.getFirstName());
        u.setMaidenName(dto.getMaidenName());
        u.setAdditionalName(dto.getAdditionalName());
        u.setActive(dto.isActive());
        return u;
    }

    public DonatorDTO donatorToDto(Donator u) {
        DonatorDTO dto = new DonatorDTO();
        dto.setId(u.getId());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setMaidenName(u.getMaidenName());
        dto.setAdditionalName(u.getAdditionalName());
        dto.setActive(u.isActive());
        return dto;
    }

}

