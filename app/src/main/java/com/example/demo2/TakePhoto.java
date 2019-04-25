package com.example.demo2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class TakePhoto extends AppCompatActivity {

    ImageView photo;
    EditText photoName;
    Button btn_takePhoto;
    Button btn_addPhoto;
    String strName;
    Uri URI;

    public static final int REQUEST_CAPTURE = 1;

    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        //Initializing variables:
        photo = findViewById(R.id.photo);
        photoName = findViewById(R.id.txtName);
        btn_takePhoto = findViewById(R.id.btn_takePhoto);
        btn_addPhoto =findViewById(R.id.btn_addPhoto);

        hasCamera();
        checkFilesPermissions();
    }

    public void takePhoto(View view) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(photoIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(photoIntent,REQUEST_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK)
        {
            //Get photo name/description
            strName = photoName.getText().toString();
            //Take photo
            Bundle extras = data.getExtras();
            Bitmap myPhoto = (Bitmap) extras.get("data");
            //Set imageview to photo
            photo.setImageBitmap(myPhoto);
            //Save image to the phone gallery
            String URL = MediaStore.Images.Media.insertImage(getContentResolver(), myPhoto, strName, strName);
            URI = Uri.parse(URL) ;
        }
    }

    public void addPhoto(View view) {
        //Save image to Firebase:
        // TODO:Check for authenication (only authenicated users can add photos)

        //  Get reference to the storage (path, where to save)
        StorageReference photoRef = storageRef.child("images/recipes/"+strName+".jpg");
        InputStream stream;
        try {
            stream = this.getContentResolver().openInputStream(URI);
            UploadTask uploadTask = photoRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URI to the uploaded content
                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    // TODO SAVE URI TO DATABASE
                    Log.d("debug", "Success TO UPLOAD IMAGE");

                }
            }).addOnFailureListener(TakePhoto.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("debug", "Failure TO UPLOAD IMAGE");
                }
            });
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    public boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void checkFilesPermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionCheck = TakePhoto.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionCheck += TakePhoto.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            }
            if (permissionCheck != 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
                }
            }
        }else{
            Log.d("debug", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

}
