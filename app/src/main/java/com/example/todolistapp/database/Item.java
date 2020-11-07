package com.example.todolistapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")

public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String priority;
    private String progress_number;
    //private String clock;
    //private int priority;
    //private String date;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
    //private String date;



    @Ignore
    public Item(String name, String description, String priority,  String progress_number, Date updatedAt) {
    //  public Item(String name, String description, String priority,  String progress_number, String clock, Date updatedAt){

        this.name = name;
        this.description = description;
        this.priority = priority;
        this.progress_number = progress_number;
       // this.clock = clock;
        this.updatedAt = updatedAt;
        //this.date = date;

    }


     public Item(int id, String name, String description, String priority,  String progress_number, Date updatedAt) {
      //public Item(int id, String name, String description, String priority,  String progress_number, String clock, Date updatedAt){

        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.progress_number = progress_number;
      //  this.clock = clock;
        this.updatedAt = updatedAt;
         // this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setNamed(int id) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public  String getProgress_number() {return progress_number;}

    public void setProgress_number( String progress_number) {this.progress_number = progress_number;}

   /* public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }*/

  /*   public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }*/
}
