package com.example.immunizationmanager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView,textViewDOB;
    View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.clientImage);
        textView=itemView.findViewById(R.id.clientName);
        textViewDOB=itemView.findViewById(R.id.birthDate);
        view=itemView;
    }
}
