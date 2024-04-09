package com.example.assignment4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Query("SELECT * FROM task_table WHERE id = :taskId LIMIT 1")
    Task getTaskById(int taskId);
    @Query("Delete FROM task_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    LiveData<List<Task>> getAllTasks();
}