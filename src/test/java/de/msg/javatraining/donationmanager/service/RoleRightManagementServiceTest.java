package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRepositoryInterface;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRightRepoInterface;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static de.msg.javatraining.donationmanager.persistence.model.ERole.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleRightManagementServiceTest {

    @Mock
    private RoleRepositoryInterface roleRepository;

    @Mock
    private RoleRightRepoInterface roleRightRepository;

    @InjectMocks
    private RoleRightManagementService roleRightManagementService;

    @Test
    public void testAddRight() {
        Role_Right roleRight = new Role_Right();

        roleRightManagementService.addRight(roleRight);

        verify(roleRightRepository, times(1)).saveRoleRight(roleRight);
    }

    @Test
    public void testRemoveRight() {
        int roleID = 1;
        ERight right = ERight.CAMP_IMPORT;

        roleRightManagementService.removeRight(roleID, right);

        verify(roleRightRepository, times(1)).deleteRoleRight(roleID, right);
    }

    @Test
    public void testFindAllRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ROLE_ADM));
        roles.add(new Role(ROLE_MGN));

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> foundRoles = roleRightManagementService.findAllRoles();

        assertNotNull(foundRoles);
        assertEquals(2, foundRoles.size());
    }

    @Test
    public void testFindAllRoleRights() {
        List<Role_Right> roleRights = new ArrayList<>();
        roleRights.add(new Role_Right());

        when(roleRightRepository.findAll()).thenReturn(roleRights);

        List<Role_Right> foundRoleRights = roleRightManagementService.findAllRoleRights();

        assertNotNull(foundRoleRights);
        assertEquals(1, foundRoleRights.size());
    }

    @Test
    public void testUpdateRole() {
        Role role = new Role(ROLE_ADM);

        when(roleRepository.saveRole(role)).thenReturn(role);

        Role updatedRole = roleRightManagementService.updateRole(role);

        assertNotNull(updatedRole);
        assertEquals(ROLE_ADM, updatedRole.getName());
    }

}
