package com.example.forsolo.findmate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.forsolo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity{
    private static final String TAG = "ProfileActivity";
    private ImageView profileImageView;
    //private LinearLayout loaderLayout;
    private RelativeLayout buttonBackgroundLayout;
    private String profilePath;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //loaderLayout = findViewById(R.id.LoaderLayout);
        profileImageView =findViewById(R.id.profileImageView);
        buttonBackgroundLayout = findViewById(R.id.buttonsBackgroundLayout);

        profileImageView.setOnClickListener(onClickListener);
        buttonBackgroundLayout.setOnClickListener(onClickListener);
        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
        findViewById(R.id.picture).setOnClickListener(onClickListener);
        findViewById(R.id.gallery).setOnClickListener(onClickListener);




    }

    public void onBackPresserd(){
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra(INTENT_PATH);
                    Glide.with(this).load(profilePath).centerCrop().override(500).into(profileImageVIew);
                    buttonBackgroundLayout.setVisibility(View.GONE);
                }
                break;
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.checkButton:
                    storageUploader();
                    break;
                case R.id.profileImageView:
                    buttonBackgroundLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.buttonsBackgroundLayout:
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.picture:
                    myStartActivity(CameraActivity.class);
                    break;
                case R.id.gallery:
                    myStartActivity(GalleryActivity.class);
                    break;
            }
        }
    };

    private void storageUploader() {
        final String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        final String major = ((EditText) findViewById(R.id.majorEditText)).getText().toString();
        final String age = ((EditText) findViewById(R.id.ageEditText)).getText().toString();
        final RadioGroup sex = ((RadioGroup) findViewById(R.id.add_sex));

        if(name.length()>0 && major.length()>2 && age.length()>1&&sex!=null){
            FirbaseStorge storge = FirebaseStorage.getInstance();
            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImageRef = storageRef.child("users/" + user.getUid() + "/profileImage.jpg");

            if (profilePath == null) {
                UserInfo userInfo = new UserInfo(name, major, age, sex);
                storeUploader(userInfo);
            } else {
                try {
                    InputStream stream = new FileInputStream(new File(profilePath));
                    UploadTask uploadTask = mountainImagesRef.putStream(stream);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return mountainImagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                UserInfo userInfo = new UserInfo(name, major, age, downloadUri.toString());
                                storeUploader(userInfo);
                            } else {
                                Toast.makeText(ProfileActivity.this, "회원정보를 보내는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    Log.e("로그", "에러: " + e.toString());
                }
            }
        } else {
            Toast.makeText(ProfileActivity.this, "회원정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        }

    private void storeUploader(UserInfo userInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "회원정보 등록을 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "회원정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT);
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }
    }


}