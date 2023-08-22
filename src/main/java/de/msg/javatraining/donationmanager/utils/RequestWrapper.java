package de.msg.javatraining.donationmanager.utils;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;

import java.util.List;

public class RequestWrapper {

    List<ERight> rights;

    ERight roleRight;
    int roleID;

    public List<ERight> getRights() {
        return rights;
    }

    public void setRights(List<ERight> rights) {
        this.rights = rights;
    }

    public ERight getRoleRight() {
        return roleRight;
    }

    public void setRoleRight(ERight roleRight) {
        this.roleRight = roleRight;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }


}
