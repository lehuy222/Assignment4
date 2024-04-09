package com.example.assignment4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

public class EditTaskActivity extends AppCompatActivity {

    EditText idEditText, titleEditText, descriptionEditText, dateEditText;
    Button updateButton, getTaskButton;
    private TaskViewModel taskViewModel;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idEditText = findViewById(R.id.idEditText);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);

        getTaskButton = findViewById(R.id.getTaskButton);
        updateButton = findViewById(R.id.updateButton);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        getTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = Integer.parseInt(idEditText.getText().toString());
                task = taskViewModel.getTaskById(ID);
                if (ID == 6){
                    titleEditText.setText("");
                    descriptionEditText.setText("");
                    dateEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Cant find the given Task ID", Toast.LENGTH_SHORT).show();
                }
                if (task != null) {
                    titleEditText.setText(task.getTitle());
                    descriptionEditText.setText(task.getDescription());
                    dateEditText.setText(task.getDueDate());
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTitle(titleEditText.getText().toString());
                task.setDescription(descriptionEditText.getText().toString());
                task.setDueDate(dateEditText.getText().toString());
                showUpdateConfirmationDialog(task);
            }
        });
    }
    void showUpdateConfirmationDialog(Task updateTask) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set message, title, and response buttons
        builder.setMessage("Are you sure you want to update this task?");
        builder.setTitle("Update Task");

        // Positive button for confirmation
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the task.
                taskViewModel.update(updateTask);

                // Optionally, notify the user
                Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Negative button for cancellation
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing.
                if (dialog != null) {
                    dialog.dismiss();
                }
                finish();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}