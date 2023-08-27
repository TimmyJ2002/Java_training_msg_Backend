package de.msg.javatraining.donationmanager.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @OneToMany(
            cascade = { CascadeType.ALL },
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "role_id")
    private List<Role_Right> rights;


    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public List<Role_Right> getRights() {
        return rights;
    }

    public void setRights(List<Role_Right> rights) {
        this.rights = rights;
    }
}
