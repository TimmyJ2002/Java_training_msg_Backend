package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.config.security.JwtUtils;
import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.service.LogService;
import de.msg.javatraining.donationmanager.service.RoleRightManagementService;
import de.msg.javatraining.donationmanager.utils.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class RightController {

    @Autowired
    RoleRightManagementService roleRightManagementService;

    @Autowired
    private LogService logService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("roles")
    public List<Role> findAllRoles() {
        return roleRightManagementService.findAllRoles();}

    @GetMapping("roles/rights")
    public List<Role_Right> findAllRoleRights() {
        return roleRightManagementService.findAllRoleRights();
    }


    @PostMapping("roles/removeRight")
    public void removeRoleRight(@NonNull HttpServletRequest request, @RequestBody RequestWrapper requestWrapper) {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        logService.create("delete", "info", "Right deleted", username, LocalDateTime.now());
        roleRightManagementService.removeRight(requestWrapper.getRoleID(), requestWrapper.getRoleRight());
    }

    @PutMapping("roles")
    public Role updateRole(@NonNull HttpServletRequest request, @RequestBody Role role) {
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        logService.create("modified", "info", "Right modified", username, LocalDateTime.now());
        return roleRightManagementService.updateRole(role);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }
}
