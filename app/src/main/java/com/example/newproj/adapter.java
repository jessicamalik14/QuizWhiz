package com.example.newproj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    Context context;
    ArrayList<categories> list;

    public adapter(Context context, ArrayList<categories> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.quizitems, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        categories c = list.get(position);
        holder.topic.setText(c.getTopic()); // Display the topic name

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, questions.class);
            intent.putExtra("topicName", c.getTopic()); // Pass the topic name
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView topic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.categoryTopic);
        }
    }
}
