package de.msg.javatraining.donationmanager.utils;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.Role;

public class RequestWrapper {

    ERight roleRight;
    int roleID;

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
