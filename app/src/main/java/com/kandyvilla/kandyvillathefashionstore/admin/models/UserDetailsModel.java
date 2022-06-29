package com.kandyvilla.kandyvillathefashionstore.admin.models;

public class UserDetailsModel {
    String name;
    String email;
    String mobileno;
    String address;
    String profileImg;


    public UserDetailsModel() {
    }

    public UserDetailsModel(String name, String email, String mobileno, String address, String profileImg) {
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.address = address;
        this.profileImg = profileImg;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
