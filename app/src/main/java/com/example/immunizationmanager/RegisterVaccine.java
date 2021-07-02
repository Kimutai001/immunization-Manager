package com.example.immunizationmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterVaccine extends AppCompatActivity {
    EditText nameVaccine;
    Button submit;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vaccine);
        nameVaccine=findViewById(R.id.vaccineName);
        submit=findViewById(R.id.vaccineRegister);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        
        reference= FirebaseDatabase.getInstance().getReference().child("vaccine");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaccineNames=nameVaccine.getText().toString().trim();

                HashMap<String,Object> vaccineMap=new HashMap<>();

                vaccineMap.put("clientName", name);
                vaccineMap.put("VaccineName",vaccineNames);

                reference.push().setValue(vaccineMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(RegisterVaccine.this, "Vaccine Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ViewVaccine.class);
                        intent.putExtra("name", name);
                        startActivity(intent);

                    }
                });

            }
        });



    }
}