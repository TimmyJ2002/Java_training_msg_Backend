package de.msg.javatraining.donationmanager.persistence.model.DTOs;

import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import jakarta.persistence.*;

import java.util.List;

public class RoleDTO {
    private Integer id;
    private ERole name;
    private List<Role_Right> rights;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public List<Role_Right> getRights() {
        return rights;
    }

    public void setRights(List<Role_Right> rights) {
        this.rights = rights;
    }
}
