package com.example.immunizationmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Classes.User;

public class Profile extends AppCompatActivity {
    TextView PName,PEmail,PPhone,logout;
    ImageView profileImage,edit;
    Button changeProfile;
    FirebaseAuth fAuth;
    StorageReference storageReference;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        PName=findViewById(R.id.userName);
        PEmail=findViewById(R.id.userEmail);
        PPhone=findViewById(R.id.userPhone);
        changeProfile=findViewById(R.id.changeProfile);
        profileImage=findViewById(R.id.Profile_Image);
        logout=findViewById(R.id.logout);
        edit=findViewById(R.id.editIcon);

        fAuth= FirebaseAuth.getInstance();

        ImageView backIcon=findViewById(R.id.backIcon);


        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userID=user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);

                if(userProfile !=null){
                    String fullNames=userProfile.fullNames;
                    String phone=userProfile.phone;
                    String email=userProfile.email;

                    PName.setText(fullNames);
                    PPhone.setText(phone);
                    PEmail.setText(email);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid());
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent intent =new Intent(Profile.this,ViewImages.class);
                        intent.putExtra("url", uri.toString());
                        startActivity(intent);
                    }
                });

            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });


        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

    }


    private  void  showUpdateDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View view=inflater.inflate(R.layout.update_profile,null);
        builder.setView(view);


        final EditText updateNameText=(EditText) view.findViewById(R.id.nameUpdate);
        final EditText updateEmailText=(EditText) view.findViewById(R.id.emailUpdate);
        final EditText updatePhoneText=(EditText) view.findViewById(R.id.phoneUpdate);
        final Button updateButton=(Button) view.findViewById(R.id.updateProfile);

        builder.setTitle("Update Profile");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNames=updateNameText.getText().toString().trim();
                String email=updateEmailText.getText().toString().trim();
                String phone=updatePhoneText.getText().toString().trim();

                if (TextUtils.isEmpty(fullNames)){
                    updateNameText.setError("Name Required");
                    updateNameText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone)){
                    updatePhoneText.setError("Phone Number Required");
                    updatePhoneText.requestFocus();
                    return;
                }
                updateDetails(fullNames,email,phone);
                alertDialog.dismiss();

            }
        });

    }

    private boolean updateDetails(String fullNames,String email,String phone){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users").child(userID);
        User user=new User(fullNames,email,phone);
        reference.setValue(user);
        Toast.makeText(this, "Details Updated Successfully", Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri=data.getData();
                // profileImage.setImageURI(imageUri);


                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 25, bout);
            byte[] bytes = bout.toByteArray();
            bout.close();

            StorageReference fileRef =storageReference.child("users/"+fAuth.getCurrentUser().getUid());
            UploadTask uploadTask  = fileRef.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(profileImage);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}