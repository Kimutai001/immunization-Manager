package com.example.immunizationmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;
import java.util.Map;

import Classes.User;

public class Profile extends AppCompatActivity {
    TextView PName,PEmail,PPhone;
    ImageView profileImage,edit,popup;
    Button changeProfile;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    ProgressBar progressBar;

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
        popup=findViewById(R.id.popup);
        progressBar=findViewById(R.id.progressbar);
        edit=findViewById(R.id.editIcon);

        fAuth= FirebaseAuth.getInstance();

        ImageView backIcon=findViewById(R.id.backIcon);


        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userID=user.getUid();

        reference.child(userID).addValueEventListener(new ValueEventListener() {
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

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(Profile.this,popup);popupmenu.getMenuInflater().inflate(R.menu.profile_menu, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        if(id==R.id.edit){
                           edit.setVisibility(View.VISIBLE);
                        } else if(id==R.id.logout){
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        return true;
                    }
                });

                popupmenu.show();
            }
        });


        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectImage();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });
      }

    private void selectImage() {
        final CharSequence[] options = {  "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private  void  showUpdateDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View view=inflater.inflate(R.layout.update_profile,null);
        builder.setView(view);


        final EditText updateNameText=(EditText) view.findViewById(R.id.nameUpdate);
        final EditText updatePhoneText=(EditText) view.findViewById(R.id.phoneUpdate);
        final Button updateButton=(Button) view.findViewById(R.id.updateProfile);

        builder.setTitle("Update Profile");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNames=updateNameText.getText().toString().trim();
                String phone=updatePhoneText.getText().toString().trim();
                if (TextUtils.isEmpty(fullNames)){
                    updateNameText.setError("Name Required");
                    updateNameText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    updatePhoneText.setError("Enter Phone Number");
                    updatePhoneText.requestFocus();
                    return;
                }
                updateDetails(fullNames,phone);
                alertDialog.dismiss();

            }
        });

    }

    private void updateDetails(String fullNames,String phone) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("fullNames", fullNames);
        user.put("phone", phone);

        reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            Uri imageUri=data.getData();
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profileImage.setImageBitmap(selectedImage);
                    }

                    break;
            }
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 75, bout);
            byte[] bytes = bout.toByteArray();
            bout.close();

            StorageReference fileRef =storageReference.child("users/"+fAuth.getCurrentUser().getUid());
            UploadTask uploadTask  = fileRef.putBytes(bytes);
            progressBar.setVisibility(View.VISIBLE);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                   fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .into(profileImage);
                        }
                    });
                    progressBar.setVisibility(View.INVISIBLE);

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

}