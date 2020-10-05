package com.example.forsolo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public DBHelper(Context context){
        super(context, "profiledb", null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String profileSql="create table tb_profile ("+
                "_id integer primary key autoincrement,"+
                "name not null,"+
                "age,"+
                "major,"+
                "photo,"+
                "sex)";
        db.execSQL(profileSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table tb_profile");
            onCreate(db);
        }

    }
}
