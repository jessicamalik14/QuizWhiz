package com.example.newproj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTopicActivity extends AppCompatActivity {
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        EditText topicTitle = findViewById(R.id.topicTitle);
        database = FirebaseDatabase.getInstance().getReference("questionTopics");

        findViewById(R.id.submitTopic).setOnClickListener(v -> {
            String title = topicTitle.getText().toString().trim();

            // Validate input
            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a topic name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Use the topic title as the key (sanitize it to avoid invalid characters)
            String topicKey = title.replace(".", "_")
                    .replace("#", "_")
                    .replace("$", "_")
                    .replace("[", "_")
                    .replace("]", "_");

            // Save the topic under its key
            database.child(topicKey).setValue(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Topic added successfully!", Toast.LENGTH_SHORT).show();
                    topicTitle.setText(""); // Clear the input field
                } else {
                    Toast.makeText(this, "Failed to add topic.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

