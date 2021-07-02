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
import com.google.firebase.database.annotations.NotNull;

public class Login extends AppCompatActivity {

    EditText LoginEmail, LoginPassword;
    Button LoginButton;
    TextView RegisterTextView, forgotTextLink;
    FirebaseAuth fAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginEmail = findViewById(R.id.loginEmail);
        LoginPassword = findViewById(R.id.loginPassword);
        RegisterTextView = findViewById(R.id.registerTextView);
        LoginButton = findViewById(R.id.loginButton);
        forgotTextLink = findViewById(R.id.forgotPassword);
        fAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginEmail.getText().toString().trim();
                String password = LoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    LoginEmail.setError("Email is required");
                    LoginEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    LoginEmail.setError("Enter valid email");
                    LoginEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    LoginPassword.setError("Password is required");
                    LoginPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    LoginPassword.setError("Password must be greater or equal to 6 characters");
                    LoginPassword.requestFocus();
                    return;
                }
                else {

                    progressDialog=new ProgressDialog(Login.this);
                    progressDialog.setTitle("Logging in");
                    progressDialog.setMessage("Please Be patient...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();


                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (fAuth.getCurrentUser().isEmailVerified()) {

                                    if(fAuth.getCurrentUser()!=null){
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();

                                    }
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                    builder.setMessage("Your Email is Not Verified! Resend Verification Email?");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Verification Email Has Been Resent", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Verification Email not Resent", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }

                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                }
            }
        });



        RegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });



        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    LoginEmail.setError("Enter your Email Address");
                    LoginEmail.requestFocus();
                } else {
                    fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Check your email for the link to reset your password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Login.this, "Email for password reset not sent: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
        finish();

    }
}
