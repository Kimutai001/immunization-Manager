package com.example.immunizationmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ViewImages extends AppCompatActivity {
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);

        view=findViewById(R.id.viewImage);
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        Glide.with(getApplicationContext())
                .load(url)
                .into(view);
    }
}