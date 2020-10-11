package com.example.forsolo.findmate.data;

public class UserInfo {

    private String userName;
    private String userProfile;

    public UserInfo(String userName, String userProfile) {
        this.userName = userName;
        this.userProfile = userProfile;
    }

    public UserInfo() {

    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserProfile() {
        return this.userProfile;
    }
}
