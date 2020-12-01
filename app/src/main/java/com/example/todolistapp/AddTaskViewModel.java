package com.example.todolistapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.todolistapp.database.AppDatabase;
import com.example.todolistapp.database.Item;

// The viewModel class allows data to survive configuration
// changes such as screen rotations
public class AddTaskViewModel extends ViewModel {


    // Variable for LiveData
    private LiveData<Item> item;

    // The constructor that  receives the database and the ItemId
    public AddTaskViewModel(AppDatabase database, int itemId) {
        item = database.taskDao().loadTaskById(itemId);

    }

    //Link to AddItem class
    public LiveData<Item> getItem() {

        return item;
    }

}
