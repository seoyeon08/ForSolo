package com.example.forsolo.findmate.activity;

import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.forsolo.R;
import com.example.forsolo.findmate.DBHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameView;
    EditText ageView;
    EditText majorView;
    RadioGroup sexView;
    Button addBtn;
    EditText profileIntro;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileIntro=(EditText)findViewById(R.id.profile_intro);
        nameView=(EditText)findViewById(R.id.add_name);
        ageView=(EditText)findViewById(R.id.add_age);
        majorView=(EditText)findViewById(R.id.add_major);
        sexView=(RadioGroup)findViewById(R.id.add_sex);
        addBtn=(Button)findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this);
        //권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();

        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    try{
                        photoFile=createImageFile();
                    } catch (IOException e){

                    }
                    if(photoFile!=null){
                        photoUri= FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
    }

    private File createImageFile() throws IOException{
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else {
                exifDegree = 0;
            }
            ((ImageView) findViewById(R.id.iv_result)).setImageBitmap(rotate(bitmap, exifDegree));

        }
    }

    private int exifOrientationToDegress(int exifOrientation){
        if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View v) {
        String name = nameView.getText().toString();
        String age = ageView.getText().toString();
        String major = majorView.getText().toString();

        if(name==null || name.equals("")){
            Toast t=Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT);
            t.show();
        }else{
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into tb_profile (name, age, major) values (?,?,?)",
            new String[]{name, age, major});
            db.close();

            finish();
        }
    }
}