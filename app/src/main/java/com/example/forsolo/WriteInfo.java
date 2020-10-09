package com.example.forsolo;

public class WriteInfo {

    private String title;
    private String time;
    private String place;
    private String person;
    private String contents;
    private String email;


    public WriteInfo(String title, String time, String place, String person, String contents, String email){
        this.title=title;
        this.time=time;
        this.place=place;
        this.person=person;
        this.contents=contents;
        this.email=email;
    }

    public String getTitle(){return this.title;}
    public String getTime(){return this.time;}
    public String getPlace(){return this.place;}
    public String getPerson(){return this.person;}
    public String getContents(){return this.contents;}
    public String getEmail(){return this.email;}
}
