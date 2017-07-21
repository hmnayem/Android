package com.game.string.blooddonararchive;

public class User {

    private String name;
    private String email;
    private String address;
    private String phone;
    private String bloodGroup;
    private String occupation;
    private int age;
    private String imageLink;
    private String userId;

    public User(String name, String email, String address, String phone, String bloodGroup, String occupation, int age, String imageLink, String userId) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.occupation = occupation;
        this.age = age;
        this.imageLink = imageLink;
        this.userId = userId;
    }

    public User(String name, String email, String address, String phone, String bloodGroup, String userId) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.occupation = "N/A";
        this.age = 0;
        this.imageLink = "N/A";
        this.userId = userId;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return getName() + " " + getEmail() + " " + getBloodGroup();
    }
}
