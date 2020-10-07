package com.example.forsolo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

//    private TextView btn_find;
//    private Button btn_report;
//    private TextView btn_login;

    public EditText loginEmailId, logInpasswd;
    private TextView btnLogIn, btn_join;

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); // 해당 화면 액션바 제거

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmailId = findViewById(R.id.main_email);
        logInpasswd = findViewById(R.id.main_pwd);
        btnLogIn = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join); // 나중에 이름 바꾸기 !
//        btn_report = findViewById(R.id.btn_report);

        // 비밀번호 찾기 , 문의 버튼 만들기

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    ManagementData mData;

                    Toast.makeText(LoginActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();

                    // 앱 상에서 전반적인 유저 데이터 저장
                    mData = ManagementData.getInstance();
                    mData.setUserData(new UserData(user.getEmail(), null));

                    Intent I = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(I);
                } else {
                    Toast.makeText(LoginActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = loginEmailId.getText().toString();
                String userPaswd = logInpasswd.getText().toString();

                if (userEmail.isEmpty()) {
                    loginEmailId.setError("Provide your Email first!");
                    loginEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    logInpasswd.setError("Enter Password!");
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
                                mData.setUserData(new UserData(user.getEmail(), null));

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);  //액티비티 이동
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

//    private TextView btn_join;
//    private TextView btn_find;
//    private Button btn_report;
//    private TextView btn_login;
//    private FirebaseAuth mAuth; //파이어베이스의 FirebaseAuth의 인스턴스 선언
//    private static final String TAG = "LoginActivity";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        mAuth = FirebaseAuth.getInstance();             // Initialize Firebase Auth
//
//        btn_join = findViewById(R.id.btn_join);
//        btn_join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
//                startActivity(intent);  //액티비티 이동
//            }
//        });
//
//        btn_find = findViewById(R.id.btn_find);
//        btn_find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, FindActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_report = findViewById(R.id.btn_report);
//        btn_report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, ReportActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_login = findViewById(R.id.btn_login);
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);  //액티비티 이동
//            }
//        });
//    }
//
//    @Override
//    //활동을 초기화 할 때 사용자가 현재 로그인 되어 있는지 확인한다.
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }
//
//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btn_signUp:
//                    SignUp();
//                    break;
//            }
//        }
//    };
//
//    //신규 사용자 가입
//    private void SignUp() {
//
//        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
//        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
//
//        if(email.length() > 0 && password.length() > 0){
//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                Toast.makeText(LoginActivity.this, "회원가입이 완료되었습니다.",
//                                        Toast.LENGTH_SHORT).show();
//                                //성공했을때 UI logic
//                            } else {
//                                if(task.getException() != null){    //예외처리 null
//                                    Toast.makeText(LoginActivity.this, "회원가입에 실패했습니다.",
//                                            Toast.LENGTH_SHORT).show();
//                                    //실패했을때 UI logic
//                                }
//                            }
//                        }
//                    });
//            }
//        else{
//            Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
//        }
//    }

}