package de.msg.javatraining.donationmanager.persistence.modelDTO;

public class CampaignDto {
    private Long id;
    private String name;
    private String purpose;

    // Constructors, getters and setters
    public CampaignDto() {
    }

    public CampaignDto(Long id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}
