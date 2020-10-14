package com.example.forsolo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends AppCompatActivity {

    public EditText loginEmailId, logInpasswd;

    //각각의 버튼들을 의마함
    private TextView btnLogIn, btn_join, btn_find;
    private Button btn_custom, btn_report;

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); // 해당 화면 액션바 제거

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmailId = findViewById(R.id.main_email);
        logInpasswd = findViewById(R.id.main_password);
        btnLogIn = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_signUp_join);
        btn_custom = findViewById(R.id.btn_custom);
        btn_report = findViewById(R.id.btn_report);
        btn_find = findViewById(R.id.btn_find);

        // 비밀번호 찾기 버튼
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, UserFindActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });

        // 문의 이동 버튼
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ReportActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });

        // 회원가입 이동 버튼
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });

        // 비회원 이동 버튼
        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    ManagementData mData;

                    Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                    // 앱 상에서 전반적인 유저 데이터 저장
                    mData = ManagementData.getInstance();
                    mData.setUserData(new UserData(user.getUid(), user.getDisplayName(), user.getEmail(), null));

                    Intent I = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(I);
                } else {
                    Toast.makeText(LoginActivity.this, "로그인 후 이용해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = loginEmailId.getText().toString();
                String userPaswd = logInpasswd.getText().toString();

                if (userEmail.isEmpty()) {
                    loginEmailId.setError("이메일을 입력하세요!");
                    loginEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    logInpasswd.setError("비밀번호를 입력하세요!");
                    logInpasswd.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                ManagementData mData;

                                // 앱 상에서 전반적인 유저 데이터 저장
                                mData = ManagementData.getInstance();
                                mData.setUserData(new UserData(user.getUid(), user.getDisplayName(), user.getEmail(), null));

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}