package com.game.string.blooddonararchive;


import java.util.Date;

public class BloodToken {

    private String title;
    private String description;
    private String hospitalName;
    private String location;
    private String blood;
    private String urgency;
    private long creatingTime;
    private String ownerId;
    private String tokenId;


    public BloodToken(String title, String description, String hospitalName, String location,
                      String blood, String urgency, long creatingTime, String ownerId, String tokenId) {
        this.title = title;
        this.description = description;
        this.hospitalName = hospitalName;
        this.location = location;
        this.blood = blood;
        this.urgency = urgency;
        this.creatingTime = creatingTime;
        this.ownerId = ownerId;
        this.tokenId = tokenId;
    }

    public BloodToken(String title, String description, String hospitalName, String location,
                      String blood, String urgency, String ownerId, String tokenId) {
        this.title = title;
        this.description = description;
        this.hospitalName = hospitalName;
        this.location = location;
        this.blood = blood;
        this.urgency = urgency;
        this.creatingTime = new Date().getTime();
        this.ownerId = ownerId;
        this.tokenId = tokenId;
    }

    public BloodToken(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public long getCreatingTime() {
        return creatingTime;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public String toString() {
        return title + " " + tokenId;
    }
}
