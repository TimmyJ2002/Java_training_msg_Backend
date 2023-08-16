package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.service.RoleRightManagementService;
import de.msg.javatraining.donationmanager.utils.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println("altceva");
        System.out.println(requestWrapper.getRoleID());

        Role_Right rr = new Role_Right();
        rr.setRoleRight(requestWrapper.getRoleRight());
        rr.setRole(roleRightManagementService.findByID(requestWrapper.getRoleID()));

        System.out.println(rr.getRole().getId());

        roleRightManagementService.addRight(rr);
    }

    @PostMapping("roles/removeRight")
    public void removeRoleRight(@RequestBody RequestWrapper requestWrapper) {

        System.out.println("cevanustiu");
        System.out.println(requestWrapper.getRoleID());

        roleRightManagementService.removeRight(requestWrapper.getRoleID(), requestWrapper.getRoleRight());
    }

}
