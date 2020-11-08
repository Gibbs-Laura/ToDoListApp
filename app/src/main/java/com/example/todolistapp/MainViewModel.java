package com.example.todolistapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.example.todolistapp.database.AppDatabase;
import com.example.todolistapp.database.Item;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Item>> items;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());

        items = appDatabase.taskDao().loadAllTasks();
    }

    public LiveData<List<Item>> getTasks() {
        return items;
    }
}

