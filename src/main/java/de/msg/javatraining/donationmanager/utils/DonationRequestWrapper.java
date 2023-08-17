package de.msg.javatraining.donationmanager.utils;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donator;

public class DonationRequestWrapper {

    int amount;

    String currency;

    int campaignID;

    int donatorID;

    String notes;

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public int getDonatorID() {
        return donatorID;
    }

    public void setDonatorID(int donatorID) {
        this.donatorID = donatorID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
