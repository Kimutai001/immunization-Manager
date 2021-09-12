package com.example.immunizationmanager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class ClientRegister extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 101;
    TextView dob;
    EditText cName;
    ImageView imageViewAdd;
    Spinner spinner;
    Button clientRegister;
    String gender;
    ProgressDialog progressDialog;
    Uri imageUri;
    boolean isImageAdded;


    private DatabaseReference root;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        cName=findViewById(R.id.childName);
        spinner=findViewById(R.id.sex);
        dob=findViewById(R.id.DOB);
        clientRegister=findViewById(R.id.clientRegister);
        imageViewAdd=findViewById(R.id.imageViewAdd);
        DatePickerDialog.OnDateSetListener listener;

        storageReference= FirebaseStorage.getInstance().getReference().child("clientImages");
        root=FirebaseDatabase.getInstance().getReference().child("clientInfo");


        DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                dob.setText(date);
            }
        };
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        ClientRegister.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        dob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });



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

                if(TextUtils.isEmpty(childName)){
                    cName.setError("Enter Child Name ");
                    cName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(childBirthDate)){
                    dob.setError("Enter Child Date Of Birth ");
                    dob.requestFocus();
                    return;
                }
                if(isImageAdded){
                    uploadImage(childName,childBirthDate);
                }
            }
        });

    }

    private void uploadImage(String childName, String childBirthDate) {
        progressDialog=new ProgressDialog(ClientRegister.this);
        progressDialog.setTitle("Registering Child");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 75, bout);
            byte[] bytes = bout.toByteArray();
            bout.close();

            String key=root.push().getKey();
            StorageReference fileRef =storageReference.child("clientImages/");
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
                            hashMap.put("Gender",gender);

                            root.push().setValue(hashMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    String key=ref.getKey();
                                    DatabaseReference reference = root.child(key);
                                    reference.child("key").setValue(key).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               Toast.makeText(ClientRegister.this, "Child Registered Successfully", Toast.LENGTH_SHORT).show();
                                               Intent intent =new Intent(ClientRegister.this,MainActivity.class);
                                               intent.putExtra("key",key);
                                               startActivity(intent);
                                               finish();
                                           }
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ClientRegister.this, "Child Registration Failed", Toast.LENGTH_SHORT).show();
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
