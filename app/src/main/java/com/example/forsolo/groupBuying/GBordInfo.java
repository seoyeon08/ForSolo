package com.example.forsolo.groupBuying;

public class GBordInfo {
    private String title;
    private String time;
    private String place;
    private String person;
    private String contents;
    private String email;
    private String date;
    private String sc;
    private String userName;
    private String userProfileUrl;

    public GBordInfo(String title, String time, String place, String person, String contents, String email, String date, String sc, String userName, String userProfileUrl) {
        this.title = title;
        this.time = time;
        this.place = place;
        this.contents = contents;
        this.email = email;
        this.date = date;
        this.sc = sc;
        this.userName = userName;
        this.userProfileUrl = userProfileUrl;
    }

    public GBordInfo(){}

    public String getTitle() {
        return this.title;
    }

    public String getTime() {
        return this.time;
    }

    public String getPlace() {
        return this.place;
    }

    public String getPerson() {
        return this.person;
    }

    public String getContents() {
        return this.contents;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDate() {
        return this.date;
    }

    public String getSC() {return this.sc;}

    public String getUserName(){return this.userName;}

    public String getUserProfileUrl() {return this.userProfileUrl;}
}
