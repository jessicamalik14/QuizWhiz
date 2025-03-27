package com.example.newproj;

import androidx.appcompat.app.AppCompatActivity;

public class questionholder extends AppCompatActivity {
    private String question;
    private String[] options;

    public questionholder(String question, String[] options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }


}
