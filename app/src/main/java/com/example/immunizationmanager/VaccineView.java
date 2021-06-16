package com.example.immunizationmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.MyAdapter;
import Classes.Vaccine;

public class VaccineView extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textViewName;
   

    private MyAdapter adapter;
    private ArrayList<Vaccine> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vaccine);

        recyclerView=findViewById(R.id.recycler);
        textViewName=findViewById(R.id.nameView);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference().child("vaccine").orderByChild("clientName").equalTo(name);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String vaccine = snap.child("VaccineName").getValue().toString();
                    Log.v("myTag", "found");

                    textViewName.setText(name);
                    Vaccine model =snap.getValue(Vaccine.class);
                    list.add(model);
                }
                adapter=new MyAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}