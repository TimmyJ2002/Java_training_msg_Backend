package de.msg.javatraining.donationmanager.utils;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.model.Donator;

public class DonationRequestWrapper {

    int amount;

    String currency;

    Campaign campaign;

    Donator donator;

    String notes;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
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
