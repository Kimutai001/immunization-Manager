package com.example.immunizationmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterVaccine extends AppCompatActivity {
    EditText nameVaccine,dateView;
    Button submit;
    Spinner administration,timeLine;
    DatabaseReference databaseReference;
    String mode,timeline;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vaccine);
        nameVaccine=findViewById(R.id.vaccineName);
        administration=findViewById(R.id.adminMode);
        timeLine=findViewById(R.id.timeLine);
        submit=findViewById(R.id.vaccineRegister);
        dateView=findViewById(R.id.date);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        RegisterVaccine.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                dateView.setText(date);
            }
        };
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
        String key=extras.getString("key");
        
        databaseReference= FirebaseDatabase.getInstance().getReference().child("clientInfo").child(key).child("vaccine");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaccineNames=nameVaccine.getText().toString().trim();
                String vaccinationDate=dateView.getText().toString().trim();
                if ((vaccineNames.isEmpty())){
                    nameVaccine.setError("Vaccine Name Required");
                    nameVaccine.requestFocus();
                    return;
                }

                HashMap<String,Object> vaccineMap=new HashMap<>();

                vaccineMap.put("clientName", name);
                vaccineMap.put("VaccineName",vaccineNames);
                vaccineMap.put("Mode",mode);
                vaccineMap.put("timeLine",timeline);
                vaccineMap.put("DateVaccinated",vaccinationDate);

                databaseReference.push().setValue(vaccineMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        String vaccineKey=ref.getKey();
                        DatabaseReference reference = databaseReference.child(vaccineKey);
                        reference.child("key").setValue(vaccineKey).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterVaccine.this, "Vaccine Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ViewVaccine.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("mode",mode );
                                    intent.putExtra("timeLine",timeline);
                                    intent.putExtra("key",key);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterVaccine.this, "Failed to record Vaccine", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });



    }
}