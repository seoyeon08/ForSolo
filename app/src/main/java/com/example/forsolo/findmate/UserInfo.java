package com.example.forsolo.findmate;

import android.widget.RadioGroup;

public class UserInfo {
    private String name;
    private String age;
    private String major;
    private String photoUrl;

    public UserInfo(String name, String age, String major,  String photoUrl){
        this.name = name;
        this.age = age;
        this.major = major;
        this.photoUrl = photoUrl;
    }

    public UserInfo(String name, String age, String major){
        this.name = name;
        this.age = age;
        this.major = major;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getAge(){
        return this.age;
    }
    public void setAge(String age){
        this.age = age;
    }
    public String getMajor(){
        return this.major;
    }
    public void setMajor(String major){
        this.major = major;
    }
    public String getPhotoUrl(){
        return this.photoUrl;
    }
    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
