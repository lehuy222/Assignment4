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
import androidx.lifecycle.ViewModelProvider;

public class DeleteTaskActivity extends AppCompatActivity {

    EditText deleteID;
    Button deleteButton;
    private TaskViewModel taskViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteID = findViewById(R.id.deleteID);
        deleteButton = findViewById(R.id.deleteButton);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = Integer.parseInt(deleteID.getText().toString());
                Task check_available = taskViewModel.getTaskById(ID);
                if (ID == 6){
                    Toast.makeText(getApplicationContext(), "Cant find the given Task ID", Toast.LENGTH_SHORT).show();
                }
                if(check_available != null){
                    showDeleteConfirmationDialog(ID);
                }
            }
        });
    }

    void showDeleteConfirmationDialog(int ID) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set message, title, and response buttons
        builder.setMessage("Are you sure you want to delete this task?");
        builder.setTitle("Delete Task");

        // Positive button for confirmation
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the task.
                taskViewModel.deleteById(ID);

                // Optionally, notify the user
                Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
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
//        finish();
    }
}