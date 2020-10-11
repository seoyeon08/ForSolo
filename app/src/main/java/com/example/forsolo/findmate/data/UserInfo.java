package com.example.forsolo.findmate.data;

import com.google.firebase.database.Exclude;

public class UserInfo {

    @Exclude
    private String user_Name;
    private String user_Email;
    private String user_Id;

    public UserInfo(String user_Email, String user_Id, String user_Name) {
        this.user_Email = user_Email;
        this.user_Id = user_Id;
        this.user_Name = user_Name;
    }

    public UserInfo() {

    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String userName) {
        this.user_Name = userName;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String userEmail) {
        this.user_Email = userEmail;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String userId) {
        this.user_Id = userId;
    }

}
