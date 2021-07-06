package com.example.immunizationmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Classes.Client;
import Classes.User;

public class ClientInformation extends AppCompatActivity {

    FloatingActionMenu floatingActionMenu;
    com.github.clans.fab.FloatingActionButton vaccineViewFloat, VaccineRegisterFloat;


    ImageView imageView,editShow,delete,editPersonalData,editFamilyData,editLocationData;
    TextView textViewName,textViewDob,textViewGender,textViewFName,textViewFContact,textViewMName,textViewMContact,textViewCounty,textViewSub;
   private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("clientInfo"),dataRef;
    StorageReference storageRef;
    String clientName, imageNameUrl,gender,counties, clientKey;



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
        textViewCounty=findViewById(R.id.countyView);
        textViewSub=findViewById(R.id.subCountyView);
        editShow=findViewById(R.id.editShow);
        delete=findViewById(R.id.delete);
        editPersonalData=findViewById(R.id.editPersonalData);
        editFamilyData=findViewById(R.id.editFamilyData);
        editLocationData=findViewById(R.id.editLocationData);

        clientKey=getIntent().getStringExtra("clientKey");
        dataRef=FirebaseDatabase.getInstance().getReference().child("clientInfo").child(clientKey);
        storageRef= FirebaseStorage.getInstance().getReference().child("clientInfo").child(clientKey);

        floatingActionMenu=findViewById(R.id.fabMenu);
        vaccineViewFloat=findViewById(R.id.vaccineViewFloat);
        VaccineRegisterFloat=findViewById(R.id.vaccineRegisterFloat);


        VaccineRegisterFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterVaccine.class);
                intent.putExtra("name", clientName);
                intent.putExtra("key", clientKey);
                startActivity(intent);
            }
        });
        vaccineViewFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewVaccine.class);
                intent.putExtra("name", clientName);
                intent.putExtra("key", clientKey);
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

        editShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPersonalData.setVisibility(View.VISIBLE);
                editFamilyData.setVisibility(View.VISIBLE);
                editLocationData.setVisibility(View.VISIBLE);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                              }
                       }) ;
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        Toast.makeText(ClientInformation.this, "Client Deleted Successfully", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        editPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            final
            public void onClick(View v) {
                showPersonalDataUpdateDialog();
            }
        });
        editFamilyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyDataUpdateDialog();
            }
        });
        editLocationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLocationUpdateDialog();
            }
        });


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
                    String county=snapshot.child("county").getValue().toString();
                    String subCounty=snapshot.child("subCounty").getValue().toString();

                    Picasso.get().load(imageNameUrl).into(imageView);
                    textViewName.setText(clientName);
                    textViewDob.setText(clientDOB);
                    textViewGender.setText(weight);
                    textViewFName.setText(fathersName);
                    textViewFContact.setText(fathersContact);
                    textViewMName.setText(mothersName);
                    textViewMContact.setText(mothersContact);
                    textViewCounty.setText(county);
                    textViewSub.setText(subCounty);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void ShowLocationUpdateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View view=inflater.inflate(R.layout.location_data_update,null);

        final Spinner  countySelect=(Spinner)view.findViewById(R.id.countyUpdate);
        final EditText subCounty=(EditText) view.findViewById(R.id.editTextSubCountyUpdate);
        final Button locationUpdate=(Button) view.findViewById(R.id.locationUpdate);

        ArrayAdapter<CharSequence> adapterCounty = ArrayAdapter.createFromResource(ClientInformation.this, R.array.counties, android.R.layout.simple_spinner_item);
        adapterCounty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          countySelect.setAdapter(adapterCounty);
          countySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                counties = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        builder.setTitle("Update Location Data");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        locationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCounty=subCounty.getText().toString();

                if(TextUtils.isEmpty(sCounty)){
                    subCounty.setError("Enter Updated Location ");
                    subCounty.requestFocus();
                    return;
                }

                updateLocationDetails(sCounty,counties);
                alertDialog.dismiss();
            }
        });
    }

    private void updateLocationDetails(String sCounty, String counties) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("clientInfo").child(clientKey);
        Map<String, Object> client = new HashMap<>();
        client.put("county", counties);
        client.put("subCounty", sCounty);

        reference.updateChildren(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }



    private void showFamilyDataUpdateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View view=inflater.inflate(R.layout.family_data_update,null);

        final EditText updateFathersName= view.findViewById(R.id.fathersNameUpdate);
        final EditText updateFathersContact=(EditText) view.findViewById(R.id.fathersContactUpdate);
        final EditText updateMotherName=(EditText) view.findViewById(R.id.mothersNameUpdate);
        final EditText updateMothersContact=(EditText) view.findViewById(R.id.mothersContactUpdate);
        final Button updateFamilyButton=(Button) view.findViewById(R.id.updateFamilyDetails);

        builder.setView(view);
        builder.setTitle("Update Family Data");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        updateFamilyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fNameUpdate=updateFathersName.getText().toString();
                String fContactUpdate=updateFathersContact.getText().toString();
                String mNameUpdate=updateMotherName.getText().toString();
                String mContactUpdate=updateMothersContact.getText().toString();

                if(TextUtils.isEmpty(fNameUpdate)){
                    updateFathersName.setError("Enter Updated Fathers Name ");
                    updateFathersName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(fContactUpdate)){
                    updateFathersContact.setError("Enter Updated Father Contact ");
                    updateFathersContact.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(mNameUpdate)){
                    updateMotherName.setError("Enter Updated Mother Name ");
                    updateMotherName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(mContactUpdate)){
                    updateMothersContact.setError("Enter Updated Mother Contact ");
                    updateMothersContact.requestFocus();
                    return;
                }

                updateFamilyInformation(fNameUpdate,fContactUpdate,mNameUpdate,mContactUpdate);
                alertDialog.dismiss();
            }
        });
    }

    private void updateFamilyInformation(String fNameUpdate, String fContactUpdate, String mNameUpdate, String mContactUpdate) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("clientInfo").child(clientKey);
        Map<String, Object> client = new HashMap<>();
        client.put("fathersName", fNameUpdate);
        client.put("fathersContact", fContactUpdate);
        client.put("mothersName",mNameUpdate);
        client.put("mothersContact",mContactUpdate);

        reference.updateChildren(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }



    private void showPersonalDataUpdateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View view=inflater.inflate(R.layout.personal_data_update,null);
        builder.setView(view);

        final EditText updateClientName=(EditText) view.findViewById(R.id.childNameUpdate);
        final ImageView calendarView=(ImageView) view.findViewById(R.id.viewDateUpdate);
        final EditText DOBUpdate=(EditText) view.findViewById(R.id.DOBUpdate);
        final Spinner  genderSelect=(Spinner)view.findViewById(R.id.sexUpdate);
        final Button updatePButton=(Button) view.findViewById(R.id.updatePData);

        DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                DOBUpdate.setText(date);
            }
        };
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        ClientInformation.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        ArrayAdapter<CharSequence> adapterCounty = ArrayAdapter.createFromResource(ClientInformation.this, R.array.gender, android.R.layout.simple_spinner_item);
        adapterCounty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSelect.setAdapter(adapterCounty);

        genderSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle("Update Personal Data");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        updatePButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cNameUpdate=updateClientName.getText().toString().trim();
                String DateOBUpdate=DOBUpdate.getText().toString().trim();

                if(TextUtils.isEmpty(cNameUpdate)){
                    updateClientName.setError("Enter Updated Name");
                    updateClientName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(DateOBUpdate)){
                    DOBUpdate.setError("Enter Updated Date Of Birth");
                    DOBUpdate.requestFocus();
                    return;
                }

                updatePersonalInformation(cNameUpdate,DateOBUpdate);
                alertDialog.dismiss();

            }
        });
    }

    private void updatePersonalInformation( String cNameUpdate,String DateOBUpdate ) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("clientInfo").child(clientKey);
        Map<String, Object> client = new HashMap<>();
        client.put("clientName", cNameUpdate);
        client.put("ClientDOB", DateOBUpdate);
        client.put("Gender",gender);

        reference.updateChildren(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }
}