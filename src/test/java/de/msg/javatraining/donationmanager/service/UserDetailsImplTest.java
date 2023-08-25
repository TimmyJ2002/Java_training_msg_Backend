package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsImplTest {

    @Test
    public void testBuild() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setActive(true);
        user.setLoginCount(3);

        Role role = new Role();
        role.setName(ERole.ROLE_ADM);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        assertNotNull(userDetails);
        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getEmail(), userDetails.getEmail());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.isActive());
        assertEquals(user.getLoginCount(), userDetails.getLoginCount());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());

        GrantedAuthority authority = new SimpleGrantedAuthority(ERole.ROLE_ADM.name());
        assertTrue(authorities.contains(authority));
    }

    @Test
    public void testEquals() {
        UserDetailsImpl user1 = new UserDetailsImpl(1L, "john", "john@example.com", "password", true, 3, new ArrayList<>());
        UserDetailsImpl user2 = new UserDetailsImpl(1L, "jane", "jane@example.com", "password", true, 2, new ArrayList<>());
        UserDetailsImpl user3 = new UserDetailsImpl(2L, "john", "john@example.com", "password", true, 3, new ArrayList<>());

        assertTrue(user1.equals(user2));  // Equal IDs
        assertFalse(user1.equals(user3)); // Different IDs
    }
    @Test
    public void testIsAccountNonExpired() {
        UserDetailsImpl userDetails = createUserDetails();

        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        UserDetailsImpl userDetails = createUserDetails();

        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        UserDetailsImpl userDetails = createUserDetails();

        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        UserDetailsImpl userDetails = createUserDetails();

        assertTrue(userDetails.isEnabled());
    }

    private UserDetailsImpl createUserDetails() {
        // Create and return an instance of UserDetailsImpl with necessary data
        // You might need to set up some mock data or provide required parameters
        return new UserDetailsImpl(
                1L,
                "username",
                "user@example.com",
                "password",
                true,
                0,
                null // You need to provide the collection of authorities here
        );
    }


}
