package com.example.forsolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forsolo.findmate.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignOutActivity extends AppCompatActivity {

    private static final String TAG = "SignOUTActivity";

    private Button btn_signout;
    private Button btn_backProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btn_signout = findViewById(R.id.btn_signout);
        btn_backProfile = findViewById(R.id.btn_backProfile);

        btn_backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignOutActivity.this, ProfileFragment.class);
                startActivity(intent);  //액티비티 이동
            }
        });


//        btn_signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(
//                        getContext(), LoginActivity.class);
//
//                // 데이터 초기화 및 생성
//                ManagementData.getInstance().delAllData();
//
//                startActivity(intent);
//
//                signOut();
//            }
//        });
//        private void signOut() {
//            firebaseAuth.getCurrentUser().delete();
//        }

    }
}