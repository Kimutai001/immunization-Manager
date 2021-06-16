package com.example.immunizationmanager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class ClientRegister extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 101;
    EditText dob,cName,cWeight,fName,fContact,mName,mContact;
    ImageView imageViewAdd,imageViewDate;
    Spinner spinner;
    Button clientRegister;
    String gender;
    ProgressDialog progressDialog;

    Uri imageUri;
    boolean isImageAdded;

    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("clientInfo");
    StorageReference storageReference;


    DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            month=month+1;
            String date=day+"/"+month+"/"+year;
            dob.setText(date);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);


        cName=findViewById(R.id.childName);
        fName=findViewById(R.id.fathersName);
        fContact=findViewById(R.id.fathersContact);
        mName=findViewById(R.id.mothersName);
        mContact=findViewById(R.id.mothersContact);
        spinner=findViewById(R.id.sex);
        dob=findViewById(R.id.DOB);
        imageViewDate=findViewById(R.id.viewDate);
        clientRegister=findViewById(R.id.clientRegister);
        imageViewAdd=findViewById(R.id.imageViewAdd);


        storageReference= FirebaseStorage.getInstance().getReference().child("clientImages");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ClientRegister.this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = "Male";
            }
        });

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        imageViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        ClientRegister.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });


        clientRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String childName=cName.getText().toString().trim();
                String childBirthDate=dob.getText().toString().trim();

                String fathersName=fName.getText().toString().trim();
                String fathersContact=fContact.getText().toString().trim();
                String mothersName=mName.getText().toString().trim();
                String mothersContact=mContact.getText().toString().trim();

                if(isImageAdded!=false && childName!=null){
                    uploadImage(childName,childBirthDate,fathersName,fathersContact,mothersName,mothersContact);
                }
            }
        });

    }

    private void uploadImage(String childName, String childBirthDate,  String fathersName, String fathersContact, String mothersName, String mothersContact) {
        progressDialog=new ProgressDialog(ClientRegister.this);
        progressDialog.setTitle("Registering Client");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 25, bout);
            byte[] bytes = bout.toByteArray();
            bout.close();

            String key=root.push().getKey();
            StorageReference fileRef =storageReference.child("clientImages/"+key);
            UploadTask uploadTask  = fileRef.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            HashMap hashMap=new HashMap();
                            hashMap.put("imageUrl",uri.toString());
                            hashMap.put("clientName",childName);
                            hashMap.put("clientDOB",childBirthDate);
                            hashMap.put("fathersName",fathersName);
                            hashMap.put("fathersContact",fathersContact);
                            hashMap.put("mothersName",mothersName);
                            hashMap.put("mothersContact",mothersContact);
                            hashMap.put("Gender",gender);

                            root.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ClientRegister.this, "Client Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }

                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                        }
                    });

        }catch (Exception ex){

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==REQUEST_CODE_IMAGE && data!=null){
            imageUri=data.getData();
            isImageAdded=true;
            imageViewAdd.setImageURI(imageUri);
        }
    }
}