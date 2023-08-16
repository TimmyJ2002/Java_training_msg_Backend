package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role dtoToRole(RoleDTO dto) {
        Role r = new Role();
        r.setId(dto.getId());
        r.setName(dto.getName());
        r.setRights(dto.getRights());
        return r;
    }

    public RoleDTO roleToDto(Role r) {
        RoleDTO dto = new RoleDTO();
        dto.setId(r.getId());
        dto.setName(r.getName());
        dto.setRights(r.getRights());
        return dto;
    }

}
