package com.example.todolistapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY priority")
    LiveData<List<Item>> loadAllTasks();

    @Insert
    void insertTask(Item item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Item item);

    @Delete
    void deleteTask(Item item);

    @Query("SELECT * FROM task WHERE id = :id")
    LiveData<Item> loadTaskById(int id);
}
