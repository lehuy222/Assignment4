package com.example.assignment4;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;
    int id;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void update(Task task) { repository.update(task);}
    public void insert(Task task) {
        repository.insert(task);
    }

    public Task getTaskById(int id) {
        return repository.getTaskById(id);
    }
    public void deleteById(int id) {repository.deleteById(id);}
    // Add methods for update, delete, etc. that call the repository

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
}