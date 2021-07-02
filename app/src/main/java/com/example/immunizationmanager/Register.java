package com.example.immunizationmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import Classes.User;

public class Register extends AppCompatActivity {

    EditText Name,PhoneNumber,Email,Password;
    Button Register;
    TextView loginTextView;
    FirebaseAuth fAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name=findViewById(R.id.editTextName);
        PhoneNumber=findViewById(R.id.editTextNumber);
        Email=findViewById(R.id.editTextEmail);
        Password=findViewById(R.id.editTextPassword);
        Register=findViewById(R.id.registerButton);
        loginTextView=findViewById(R.id.textViewLogin);

        fAuth= FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString().trim();
                String password=Password.getText().toString().trim();
                String fullName=Name.getText().toString();
                String phone=PhoneNumber.getText().toString();

                if(TextUtils.isEmpty(fullName)){
                    Name.setError("Full Name is required");
                    Name.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    PhoneNumber.setError("Phone Number is required");
                    PhoneNumber.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is required");
                    Email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Enter valid email");
                    Email.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is required");
                    return;
                }
                if(password.length ()<6){
                    Password.setError("Password must be greater than or equal to 6 characters");
                    return;
                }
                else {

                    progressDialog=new ProgressDialog(com.example.immunizationmanager.Register.this);
                    progressDialog.setTitle("Registering");
                    progressDialog.setMessage("Please Be patient...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            User user = new User(fullName, phone, email);
                                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(com.example.immunizationmanager.Register.this);
                                                    builder.setMessage("User Registered Successfully,A verification Email has been sent to You");
                                                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            startActivity(new Intent(getApplicationContext(), Login.class));
                                                            finish();
                                                        }
                                                    });
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(com.example.immunizationmanager.Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(com.example.immunizationmanager.Register.this, " Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(com.example.immunizationmanager.Register.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });


        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }

        });
    }
    @Override
    public void onBackPressed() {
        progressDialog.dismiss();

    }
}