package com.example.todolistapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

// @Entity is a room annotation, it will create a sqLite table for the item object
@Entity(tableName = "item")

// Entity is a java class that represents one table in our room database
public class Item {

    // unique id everytime a new row is inserted
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;
    private String description;
    private String progress;
    private String progress_number;
    private String date;
    private String time;


    @Ignore
    public Item(String category,String description,String progress, String progress_number,String date,String time){
        this.category        =  category;
        this.description     =  description;
        this.progress        =  progress;
        this.progress_number =  progress_number;
        this.date            =  date;
        this.time            =  time;
    }


    public Item(int id,String category,String description,String progress,String progress_number,String date,String time){
        this.id              = id;
        this.category        = category;
        this.description     = description;
        this.progress        = progress;
        this.progress_number = progress_number;
        this.date            = date;
        this.time            = time;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getCategory() { return category; }

    public String getProgress() { return progress; }

    public  String getProgress_number() { return progress_number; }








}
