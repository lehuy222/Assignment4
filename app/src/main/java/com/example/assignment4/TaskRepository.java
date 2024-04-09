package com.example.assignment4;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    public Task task;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void update(Task task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }
    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    // Add update, delete, and possibly other methods similarly...
    public void deleteById(int id) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteById(id);
        });
    }

    public Task getTaskById(int id) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            task = taskDao.getTaskById(id);
        });
        Log.d("TaskRepo", "dmm");
        return task;
    }
    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(final Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }
}