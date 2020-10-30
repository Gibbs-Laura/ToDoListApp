package com.example.todolistapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.todolistapp.database.AppDatabase;
import com.example.todolistapp.database.Item;

// COMPLETED (5) Make this class extend ViewModel
public class AddTaskViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the Item object wrapped in a LiveData
    private LiveData<Item> task;


    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);

    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<Item> getTask() {
        return task;
    }

}
