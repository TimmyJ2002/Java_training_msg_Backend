package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;

import java.util.List;

public interface RoleRightRepoInterface {

    void saveRoleRight(Role_Right rr);
    void deleteRoleRight(int roleID, ERight right);
    List<Role_Right> findAll();

    Role_Right findByRoleAndRight(Role role, ERight right);

}
