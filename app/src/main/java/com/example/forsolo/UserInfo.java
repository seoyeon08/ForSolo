package com.example.forsolo;

public class UserInfo {

    private String name;
    private String age;
    private String major;
    private String photoUrl;
    private String Intro;
    private String gender;

    public UserInfo(String name, String age, String major, String Intro, String gender, String photoUrl){
        this.name = name;
        this.age = age;
        this.major = major;
        this.Intro = Intro;
        this.gender=gender;
        this.photoUrl = photoUrl;
    }

    public UserInfo(String name, String age, String major, String Intro, String gender){
        this.name = name;
        this.age = age;
        this.major = major;
        this.Intro=Intro;
        this.gender=gender;
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
        this.Intro = intro;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
