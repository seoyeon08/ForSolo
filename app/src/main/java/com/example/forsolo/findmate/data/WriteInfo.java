package com.example.forsolo.findmate.data;

public class WriteInfo {

    private String title;
    private String time;
    private String place;
    private String person;
    private String contents;
    private String email;
    private String date;


    public WriteInfo(String title, String time, String place, String person, String contents, String email, String date){
        this.title=title;
        this.time=time;
        this.place=place;
        this.person=person;
        this.contents=contents;
        this.email=email;
        this.date=date;
    }

    public String getTitle(){return this.title;}
    public String getTime(){return this.time;}
    public String getPlace(){return this.place;}
    public String getPerson(){return this.person;}
    public String getContents(){return this.contents;}
    public String getEmail(){return this.email;}
    public String getDate(){return this.date;}
}
