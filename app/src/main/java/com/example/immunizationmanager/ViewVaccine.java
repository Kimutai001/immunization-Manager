package com.example.immunizationmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.CoreComponentFactory;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.allyants.notifyme.NotifyMe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import Adapters.MyAdapter;
import Classes.Vaccine;

public class ViewVaccine extends AppCompatActivity {
    ImageView imageView,reminderPop;
    RecyclerView recyclerView;
    TextView textViewName;
    private MyAdapter adapter;
    private ArrayList<Vaccine> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vaccine);

        recyclerView = findViewById(R.id.recycler);
        textViewName = findViewById(R.id.nameView);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String key = extras.getString("key");


        textViewName.setText(name);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter = new MyAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);


        Query query = FirebaseDatabase.getInstance().getReference().child("clientInfo").child(key).child("vaccine");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Vaccine vaccine = snap.getValue(Vaccine.class);
                    list.add(vaccine);
                    adapter.notifyItemInserted(list.size());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}