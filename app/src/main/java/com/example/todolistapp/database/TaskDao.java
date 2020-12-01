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

    @Query("SELECT * FROM item ORDER BY progress")
    LiveData<List<Item>> loadAllTasks();

    @Insert
    void insertTask(Item item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Item item);

    @Delete
    void deleteTask(Item item);

    // As soon there is any changes in the item table the value in Item will be updated
    // LiveData allows to observe the Item object.
    @Query("SELECT * FROM item WHERE id = :id")
    LiveData<Item> loadTaskById(int id);
}
