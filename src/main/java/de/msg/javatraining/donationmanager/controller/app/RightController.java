package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.service.RoleRightManagementService;
import de.msg.javatraining.donationmanager.utils.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RightController {

    @Autowired
    RoleRightManagementService roleRightManagementService;

    @GetMapping("roles")
    public List<Role> findAllRoles() {
        return roleRightManagementService.findAllRoles();}

    @GetMapping("roles/rights")
    public List<Role_Right> findAllRoleRights() {return roleRightManagementService.findAllRoleRights();}

    @PostMapping("roles/addRight")
    public void addRoleRight(@RequestBody RequestWrapper requestWrapper) {

        Role_Right rr = new Role_Right();
        rr.setRoleRight(requestWrapper.getRoleRight());
        rr.setRole(roleRightManagementService.findByID(requestWrapper.getRoleID()));

        roleRightManagementService.addRight(rr);
    }

    @PostMapping("roles/removeRight")
    public void removeRoleRight(@RequestBody RequestWrapper requestWrapper) {


        roleRightManagementService.removeRight(requestWrapper.getRoleID(), requestWrapper.getRoleRight());
    }

    @PutMapping("roles/updateRight")
    public void updateRoleRight(@RequestBody Role role) {
        roleRightManagementService.updateRight(role);
    }

}
