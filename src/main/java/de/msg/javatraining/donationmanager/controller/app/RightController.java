package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.service.RoleRightManagementService;
import de.msg.javatraining.donationmanager.utils.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class RightController {

    @Autowired
    RoleRightManagementService roleRightManagementService;

    @GetMapping("roles")
    public List<Role> findAllRoles() {
        return roleRightManagementService.findAllRoles();}

    @GetMapping("roles/rights")
    public List<Role_Right> findAllRoleRights() {
        return roleRightManagementService.findAllRoleRights();
    }


    @PostMapping("roles/removeRight")
    public void removeRoleRight(@RequestBody RequestWrapper requestWrapper) {
        roleRightManagementService.removeRight(requestWrapper.getRoleID(), requestWrapper.getRoleRight());
    }

    @PutMapping("roles")
    public Role updateRole(@RequestBody Role role) {
        return roleRightManagementService.updateRole(role);
    }

}
