package de.msg.javatraining.donationmanager.persistence.repository.impl;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;
import de.msg.javatraining.donationmanager.persistence.model.Role_Right;
import de.msg.javatraining.donationmanager.persistence.repository.RoleRightRepoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional
public class RoleRightRepoInterfaceImpl implements RoleRightRepoInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveRoleRight(Role_Right rr) {
        em.persist(rr);
        em.flush();
    }

    @Override
    public void deleteRoleRight(int roleID, ERight right) {
        Query query = em.createNativeQuery("DELETE FROM roleRight rr WHERE rr.roleRight = ? AND rr.role_id = ?");
        query.setParameter(1, right.name());
        query.setParameter(2, roleID);
        query.executeUpdate();
    }

    @Override
    public List<Role_Right> findAll() {
        return em.createQuery("select rr from Role_Right rr", Role_Right.class).getResultList();
//        Query query = em.createNativeQuery("SELECT * FROM roleRight");
//        return query.getResultList();
    }

    @Override
    public Role_Right findByRoleAndRight(Role role, ERight right) {
        TypedQuery<Role_Right> query = em.createQuery(
                "SELECT rr FROM Role_Right rr WHERE rr.role.id = '" + role.getId() + "' AND rr.roleRight = '" + right + "'", Role_Right.class);
        return query.getSingleResult();
    }


}
