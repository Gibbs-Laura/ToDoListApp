package com.example.todolistapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/* ROOM DATABASE  */

// @Database is a room annotations
@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolistapp6";

    // We have to turn this class into singleton
    private static AppDatabase instance;

    // create a singleton database instance
    public static AppDatabase getInstance(Context context) {
        if (instance == null) { // Instantiate if we don't have already an instance
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return instance; // return an existing instance
    }

    public abstract TaskDao taskDao();

}

