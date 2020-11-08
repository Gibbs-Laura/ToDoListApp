package com.example.todolistapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.example.todolistapp.database.AppDatabase;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase appDatabase;
    private final int mTaskId;

    public AddTaskViewModelFactory(AppDatabase database, int itemId) {
        appDatabase = database;
        mTaskId = itemId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddTaskViewModel(appDatabase, mTaskId);
    }
}
