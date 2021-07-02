package com.example.immunizationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClientInformation extends AppCompatActivity {
    FloatingActionMenu floatingActionMenu;
    com.github.clans.fab.FloatingActionButton vaccineViewFloat, VaccineRegisterFloat;


    ImageView imageView;
    TextView textViewName,textViewDob,textViewGender,textViewFName,textViewFContact,textViewMName,textViewMContact;
   private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("clientInfo");
    String clientName, imageNameUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_information);

        imageView=findViewById(R.id.clientImageView);
        textViewName=findViewById(R.id.clientNameView);
        textViewDob=findViewById(R.id.clientDOBView);
        textViewGender=findViewById(R.id.genderView);
        textViewFName=findViewById(R.id.fathersNameView);
        textViewFContact=findViewById(R.id.fathersContactView);
        textViewMName=findViewById(R.id.mothersNameView);
        textViewMContact=findViewById(R.id.mothersContactView);

        floatingActionMenu=findViewById(R.id.fabMenu);
        vaccineViewFloat=findViewById(R.id.vaccineViewFloat);
        VaccineRegisterFloat=findViewById(R.id.vaccineRegisterFloat);


        VaccineRegisterFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterVaccine.class);
                intent.putExtra("name", clientName);
                startActivity(intent);
            }
        });
        vaccineViewFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewVaccine.class);
                intent.putExtra("name", clientName);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ClientInformation.this,ViewImages.class);
                intent.putExtra("url",imageNameUrl);
                startActivity(intent);
            }
        });



        String clientKey=getIntent().getStringExtra("clientKey");
        root.child(clientKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    clientName=snapshot.child("clientName").getValue().toString();
                    imageNameUrl=snapshot.child("imageUrl").getValue().toString();
                    String clientDOB=snapshot.child("clientDOB").getValue().toString();
                    String weight=snapshot.child("Gender").getValue().toString();
                    String fathersName=snapshot.child("fathersName").getValue().toString();
                    String fathersContact=snapshot.child("fathersContact").getValue().toString();
                    String mothersName=snapshot.child("mothersName").getValue().toString();
                    String mothersContact=snapshot.child("mothersContact").getValue().toString();



                    Picasso.get().load(imageNameUrl).into(imageView);
                    textViewName.setText(clientName);
                    textViewDob.setText(clientDOB);
                    textViewGender.setText(weight);
                    textViewFName.setText(fathersName);
                    textViewFContact.setText(fathersContact);
                    textViewMName.setText(mothersName);
                    textViewMContact.setText(mothersContact);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}