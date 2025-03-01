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

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }
}
