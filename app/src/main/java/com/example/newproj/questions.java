package com.example.newproj;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class questions extends AppCompatActivity {
    private RecyclerView questionsview;
    private Button submit;
    private TextView questionTextView;
    private MaterialCardView[] optionCards = new MaterialCardView[4];
    private TextView[] optionTextViews = new TextView[4];

    private String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        init();

        // Get the topic name passed from the adapter
        topicName = getIntent().getStringExtra("topicName");

        // Load the questions for the topic
        loadQuestions(topicName);
    }

    private void init() {
        questionsview = findViewById(R.id.recycle);
        submit = findViewById(R.id.submit);
        questionTextView = findViewById(R.id.question);

        // Initialize the option cards and their TextViews
        optionCards[0] = findViewById(R.id.optionCard1);
        optionCards[1] = findViewById(R.id.optionCard2);
        optionCards[2] = findViewById(R.id.optionCard3);
        optionCards[3] = findViewById(R.id.optionCard4);

        optionTextViews[0] = optionCards[0].findViewById(R.id.optionText1);
        optionTextViews[1] = optionCards[1].findViewById(R.id.optionText2);
        optionTextViews[2] = optionCards[2].findViewById(R.id.optionText3);
        optionTextViews[3] = optionCards[3].findViewById(R.id.optionText4);

        // Set onClick listeners for each option card
        for (int i = 0; i < optionCards.length; i++) {
            final int index = i; // Create a final variable for the listener
            optionCards[i].setOnClickListener(v -> {
                // Handle option selection
                Log.d("OptionSelected", "Option " + (index + 1) + " clicked");
                // Implement your logic for handling the selected option here
                // For example, you might want to highlight the selected option:
                for (MaterialCardView card : optionCards) {
                    card.setChecked(false); // Reset all cards
                }
                optionCards[index].setChecked(true); // Highlight selected option
            });
        }

        // Handle submit button click
        submit.setOnClickListener(v -> {
            // Implement your logic for the submit action
            Log.d("SubmitClicked", "Submit button clicked");
            // You might want to check which option was selected and proceed accordingly
        });
    }

    private void loadQuestions(String topic) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("questionTopics").child(topic).child("questions");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<questionholder> questions = new ArrayList<>();

                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    // Assuming the correct answer is the first field in the snapshot
                    String questionText = questionSnapshot.child("question").getValue(String.class);
                    String[] options = new String[4];

                    // Collect the options; adjust if your DB structure is different
                    options[0] = questionSnapshot.child("optionA").getValue(String.class);
                    options[1] = questionSnapshot.child("optionB").getValue(String.class);
                    options[2] = questionSnapshot.child("optionC").getValue(String.class);
                    options[3] = questionSnapshot.child("optionD").getValue(String.class);

                    questions.add(new questionholder(questionText, options));
                }

                // Update the UI on the main thread
                runOnUiThread(() -> {
                    if (!questions.isEmpty()) {
                        displayQuestion(questions.get(0)); // Display the first question
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("DatabaseError", "Error retrieving data", error.toException());
            }
        });
    }


    private void displayQuestion(questionholder question) {
        String[] options = question.getOptions();
        questionTextView.setText(question.getQuestion());

        for (int i = 0; i < optionTextViews.length; i++) {
            if (i < options.length) {
                optionTextViews[i].setText(options[i]);
            } else {
                optionTextViews[i].setText(""); // Clear text if there are fewer options
            }
        }
    }

}
