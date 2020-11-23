package com.example.forsolo.groupRecipe;

public class RecipeWriteInfo  {

    private String title;
    private String time;
    private String place;
    private String contents;
    private String email;
    private String date;
    private String sc;
    private String name;
    private String profileURL;


    public RecipeWriteInfo(String title, String time, String place, String contents, String email, String date, String sc, String name, String profileURL){
        this.title=title;
        this.time=time;
        this.place=place;
        this.contents=contents;
        this.email=email;
        this.date=date;
        this.sc=sc;
        this.name=name;
        this.profileURL=profileURL;
    }

    public String getTitle(){return this.title;}
    public String getTime(){return this.time;}
    public String getPlace(){return this.place;}
    public String getContents(){return this.contents;}
    public String getEmail(){return this.email;}
    public String getDate(){return this.date;}
    public String getSc(){return this.sc;}
    public String getUserName(){return this.name;}
    public String getUserProfileURL(){return this.profileURL;}
}
