package com.dreampixel.luxevistaresort;

public class User {

    private int user_id;
    private String fullName;
    private String email;
    private String contact;
    private String dob;
    private String gender;
    private String password;
    private byte[] profileImage;

    public User(int user_id, String fullName, String email, String contact, String dob, String gender, String password, byte[] profileImage) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
        this.password = password;
        this.profileImage = profileImage;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
