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

    @PostMapping("roles/updateRight")
    public void updateRoleRight(@RequestBody RequestWrapper requestWrapper) {
        Role oldRole = roleRightManagementService.findByID(requestWrapper.getRoleID());
        List<ERight> selectedRights = requestWrapper.getRights();
        AtomicBoolean exists = new AtomicBoolean(false);
        Role_Right rr = new Role_Right();
        oldRole.getRights().forEach((right) -> {
            if (!selectedRights.contains(right)) {
                roleRightManagementService.removeRight(requestWrapper.getRoleID(), right.getRoleRight());
            }
        });
        selectedRights.forEach((right) -> {
            if (!oldRole.getRights().contains(right)) {

                rr.setRoleRight(requestWrapper.getRoleRight());
                rr.setRole(roleRightManagementService.findByID(requestWrapper.getRoleID()));

                roleRightManagementService.addRight(rr);
            }
        });

    }

}
