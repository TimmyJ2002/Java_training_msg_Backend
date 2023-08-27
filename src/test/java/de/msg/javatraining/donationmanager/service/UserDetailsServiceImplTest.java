package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepositoryInterface userRepositoryInterface;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role(ERole.ROLE_ADM));
        user.setRoles(roles);
        userRepositoryInterface.save(user);
        when(userRepositoryInterface.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());

        verify(userRepositoryInterface, times(1)).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        String username = "nonExistentUser";

        when(userRepositoryInterface.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username));

        verify(userRepositoryInterface, times(1)).findByUsername(username);
    }


}
