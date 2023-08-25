package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.DTOs.RoleDTO;
import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRightRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.msg.javatraining.donationmanager.persistence.model.ERole.*;

@Service
public class RoleRightManagementService {

    @Autowired
    RoleRepositoryInterface roleRepo;

    @Autowired
    RoleRightRepoInterface roleRightRepo;

    public void addRight(Role_Right rr) {
        roleRightRepo.saveRoleRight(rr);
    }

    public void removeRight(int roleID, ERight right) {
        roleRightRepo.deleteRoleRight(roleID, right);
    }

//    public Role findByID(int roleID) {
//        switch(roleID) {
//            case 1:
//                return roleRepo.findByName(ROLE_ADM);
//            case 2:
//                return roleRepo.findByName(ROLE_MGN);
//            case 3:
//                return roleRepo.findByName(ROLE_CEN);
//            default:
//                return roleRepo.findByName(ROLE_REP);
//        }
//    }
//
//    public Role_Right findRoleRightByRoleAndRight(Role role, ERight right) {
//        return roleRightRepo.findByRoleAndRight(role, right);
//    }

    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }

    public List<Role_Right> findAllRoleRights() {
        return roleRightRepo.findAll();
    }

    public Role updateRole(Role role) {
        return roleRepo.saveRole(role);
    }
}
