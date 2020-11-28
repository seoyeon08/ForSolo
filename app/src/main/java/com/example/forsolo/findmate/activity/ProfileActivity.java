package com.example.forsolo.findmate.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forsolo.UserInfo;
import com.bumptech.glide.Glide;
import com.example.forsolo.R;
import com.example.forsolo.findmate.CameraActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.forsolo.findmate.Util.INTENT_PATH;
import static com.example.forsolo.findmate.Util.showToast;

public class ProfileActivity extends AppCompatActivity{
    private static final String TAG = "ProfileActivity";
    private ImageView profileImageView;
    private RelativeLayout buttonBackgroundLayout;
    private Button btn_capture;
    private FirebaseUser user;
    private Uri profileImageUri;
    boolean isChanged= true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImageView =findViewById(R.id.profileImageView);
        buttonBackgroundLayout = findViewById(R.id.buttonsBackgroundLayout);
        btn_capture=findViewById(R.id.btn_capture);

        btn_capture.setOnClickListener(onClickListener);
        buttonBackgroundLayout.setOnClickListener(onClickListener);
        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
        findViewById(R.id.picture).setOnClickListener(onClickListener);
        findViewById(R.id.gallery).setOnClickListener(onClickListener);

    }

    public void clickBtn(View view) {
        //프로필 이미지 선택하도록 Gallery 앱 실행
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra(INTENT_PATH);
                    Glide.with(this).load(profilePath).centerCrop().override(500).into(profileImageView);
                    profileImageUri = Uri.fromFile(new File(profilePath));
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    Log.d(TAG, "onActivityResult: store-Uri="+ profileImageUri);
                }
                break;
            }
            case 10:{
                if(resultCode==RESULT_OK){
                    profileImageUri = data.getData();
                    //Picasso 라이브러리는 퍼미션 없어도 됨.
                    Picasso.get().load(profileImageUri).into(profileImageView);
                    Log.d(TAG, "onActivityResult: capture-Uri="+ profileImageUri);
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
                case R.id.btn_capture:
                    buttonBackgroundLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.buttonsBackgroundLayout:
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.picture:
                    myStartActivity(CameraActivity.class);
                    break;
                case R.id.gallery:
                    clickBtn(profileImageView);
                    break;
            }
        }
    };

    private void storageUploader() {
        final String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        final String major = ((EditText) findViewById(R.id.majorEditText)).getText().toString();
        final String age = ((EditText) findViewById(R.id.ageEditText)).getText().toString();
        final String Intro = ((EditText) findViewById(R.id.profile_intro)).getText().toString();
        final String gender = ((EditText) findViewById(R.id.gender)).getText().toString();

        if(name.length()>0 && major.length()>1 && age.length()>1&&Intro.length()>0 &&gender.length()>1){
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImageRef = storageRef.child("users/" + user.getUid() + "/profileImage.jpg");

            Log.d(TAG, "storageUploader: uri="+ profileImageUri);

            if (profileImageUri ==null) {
                UserInfo userInfo = new UserInfo(name, age, major, Intro, gender);
                storeUploader(userInfo);
            } else {
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                //2. 업로드할 파일의 node를 참조하는 객체
                //파일 명이 중복되지 않도록 날짜를 이용
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String filename = sdf.format(new Date()) + ".png";//현재 시간으로 파일명 지정 20191023142634
                //원래 확장자는 파일의 실제 확장자를 얻어와서 사용해야함. 그러려면 이미지의 절대 주소를 구해야함.
                final StorageReference imgRef = firebaseStorage.getReference("users/" + filename);

                final UploadTask uploadTask = imgRef.putFile(profileImageUri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String profileUrl = taskSnapshot.getUploadSessionUri().toString();
                        Log.d(TAG, "onSuccess: " + profileImageUri.toString() + "=>" + taskSnapshot.getUploadSessionUri().toString());

                        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UserInfo userInfo = new UserInfo(name, age, major, Intro, gender, uri.toString());
                                storeUploader(userInfo);
                            }
                        });
                        Toast.makeText(ProfileActivity.this, "프로필 저장 완료", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            showToast(ProfileActivity.this, "회원정보를 입력해주세요.");
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