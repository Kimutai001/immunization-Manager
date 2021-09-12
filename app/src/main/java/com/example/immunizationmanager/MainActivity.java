package com.example.immunizationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Classes.Child;


public class MainActivity extends AppCompatActivity {
    EditText  inputSearch;
    RecyclerView recyclerView;
    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private Query root=db.getReference().child("clientInfo");


   FirebaseRecyclerOptions<Child>options;
   FirebaseRecyclerAdapter<Child,MyViewHolder>adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputSearch=findViewById(R.id.inputSearch);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);


        ImageView profileIcon = findViewById(R.id.profileIcon);
        ImageView addIcon = findViewById(R.id.addIcon);

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClientRegister.class));
            }
        });
        loadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    loadData(s.toString());
                }else{
                    loadData("");
                }

            }
        });

    }

    private void loadData(String data) {

        Query query=root.orderByChild("clientName").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Child>().setQuery(query, Child.class).build();
        adapter=new FirebaseRecyclerAdapter<Child, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull Child child) {
                myViewHolder.textView.setText(child.getClientName());
                myViewHolder.textViewDOB.setText(child.getClientDOB());

                Glide.with(MainActivity.this)
                        .load(child.getImageUrl())
                        .into(myViewHolder.imageView);
                myViewHolder.view.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this, ChildInformation.class);
                        intent.putExtra("clientKey",getRef(i).getKey());
                        startActivity(intent);
                    }
                });

            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolder(v);

            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}