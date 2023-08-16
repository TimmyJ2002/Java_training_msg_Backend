package de.msg.javatraining.donationmanager.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="roleRight")
public class Role_Right {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 64)
    private ERight roleRight;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="role_id")
    @JsonBackReference
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ERight getRoleRight() {
        return roleRight;
    }

    public void setRoleRight(ERight roleRight) {
        this.roleRight = roleRight;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
