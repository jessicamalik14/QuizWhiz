package com.example.newproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class addquestions extends AppCompatActivity {
    DatabaseReference database;
    ArrayList<String> topics = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestions);

        // Initialize database reference
        database = FirebaseDatabase.getInstance().getReference("questionTopics");

        Spinner topicSpinner = findViewById(R.id.topicSpinner);
        EditText questionInput = findViewById(R.id.questionInput);
        EditText optionAInput = findViewById(R.id.optionAInput);
        EditText optionBInput = findViewById(R.id.optionBInput);
        EditText optionCInput = findViewById(R.id.optionCInput);
        EditText optionDInput = findViewById(R.id.optionDInput);
        EditText correctAnswerInput = findViewById(R.id.correctAnswerInput);

        // Setup the spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topicSpinner.setAdapter(adapter);

        // Load existing topics from Firebase
        loadTopics();

        topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle topic selection if needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        findViewById(R.id.submitQuestion).setOnClickListener(v -> {
            String selectedTopic = topics.get(topicSpinner.getSelectedItemPosition());
            String question = questionInput.getText().toString();
            String optionA = optionAInput.getText().toString();
            String optionB = optionBInput.getText().toString();
            String optionC = optionCInput.getText().toString();
            String optionD = optionDInput.getText().toString();
            String correctAnswer = correctAnswerInput.getText().toString();

            // Create a unique key for the question
            String questionKey = database.child(selectedTopic).child("questions").push().getKey();

            // Create a HashMap for the question data
            HashMap<String, Object> questionMap = new HashMap<>();
            questionMap.put("question", question);
            questionMap.put("optionA", optionA);
            questionMap.put("optionB", optionB);
            questionMap.put("optionC", optionC);
            questionMap.put("optionD", optionD);
            questionMap.put("correctAnswer", correctAnswer);

            // Store the question in Firebase
            if (questionKey != null) {
                database.child(selectedTopic).child("questions").child(questionKey).setValue(questionMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Question added successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to add question.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void loadTopics() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                topics.clear(); // Clear the list before adding new topics
                for (DataSnapshot topicSnapshot : dataSnapshot.getChildren()) {
                    String topicName = topicSnapshot.getKey(); // Get the topic name directly from the key
                    if (topicName != null) {
                        topics.add(topicName); // Add the topic name to the list
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter of changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(addquestions.this, "Failed to load topics.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
