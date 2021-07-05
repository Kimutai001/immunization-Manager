package com.example.immunizationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterVaccine extends AppCompatActivity {
    EditText nameVaccine,Date;
    Button submit;
    Spinner administration,timeLine;
    DatabaseReference reference;
    String mode,timeline,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vaccine);
        nameVaccine=findViewById(R.id.vaccineName);
        administration=findViewById(R.id.adminMode);
        timeLine=findViewById(R.id.timeLine);
        submit=findViewById(R.id.vaccineRegister);

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        Date=findViewById(R.id.date);
        Date.setText(currentDate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegisterVaccine.this, R.array.mode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        administration.setAdapter(adapter);
        administration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mode = "Oral";
            }
        });

        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(RegisterVaccine.this, R.array.timeline, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeLine.setAdapter(adapterTime);
        timeLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String time = parent.getItemAtPosition(position).toString();
                 timeline= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                timeline = "Received";
            }
        });


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
                vaccineMap.put("Mode",mode);
                vaccineMap.put("timeLine",timeline);
                vaccineMap.put("DateVaccinated",currentDate);

                reference.push().setValue(vaccineMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(RegisterVaccine.this, "Vaccine Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ViewVaccine.class);
                        intent.putExtra("name", name);
                        intent.putExtra("mode",mode );
                        intent.putExtra("timeLine",timeline);
                        startActivity(intent);

                    }
                });

            }
        });



    }
}