package com.example.newproj;
public class categories{
    private String topic;

    // Constructor
    public categories() {
        // Default constructor required for calls to DataSnapshot.getValue(categories.class)
    }

    public categories(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
