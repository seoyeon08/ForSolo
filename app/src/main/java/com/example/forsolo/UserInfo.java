package com.example.forsolo;

public class UserInfo {

    private String name;
    private String age;
    private String major;
    private String photoUrl;
    private String Intro;

    public UserInfo(String name, String age, String major, String Intro, String photoUrl){
        this.name = name;
        this.age = age;
        this.major = major;
        this.Intro = Intro;
        this.photoUrl = photoUrl;
    }

    public UserInfo(String name, String age, String major, String Intro){
        this.name = name;
        this.age = age;
        this.major = major;
        this.Intro=Intro;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }
}
