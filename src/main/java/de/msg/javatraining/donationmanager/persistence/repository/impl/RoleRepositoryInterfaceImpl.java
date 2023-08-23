package de.msg.javatraining.donationmanager.persistence.repository.impl;

import de.msg.javatraining.donationmanager.persistence.model.ERole;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRepositoryInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class RoleRepositoryInterfaceImpl implements RoleRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role saveRole(Role r) {
        em.merge(r);
        em.flush();
        return r;
    }

    @Override
    public void deleteRole(Role r) {
        em.remove(r);
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role findByName(ERole name) {
        TypedQuery<Role> query = em.createQuery(
                "SELECT r FROM Role r WHERE r.name = '" + name + "'", Role.class);
        return query.getSingleResult();
    }
}
