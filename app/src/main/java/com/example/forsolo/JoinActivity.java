package com.example.forsolo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends AppCompatActivity {

    private TextView btn_backLogin;
    private FirebaseAuth mAuth; //파이어베이스의 FirebaseAuth의 인스턴스 선언
    private static final String TAG = "JoinActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mAuth = FirebaseAuth.getInstance();             // Initialize Firebase Auth

        //회원가입 버튼
        findViewById(R.id.btn_signUp).setOnClickListener(onClickListener);

        btn_backLogin = findViewById(R.id.btn_backLogin);
        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });
    }

    @Override
    //활동을 초기화 할 때 사용자가 현재 로그인 되어 있는지 확인한다.
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_signUp:
                    signUp();
                    break;
            }
        }
    };

    //신규 사용자 가입
    /*private void Join() {

        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "회원가입에 성공했습니다.");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //성공했을때 UI logic
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(JoinActivity.this, "회원가입에 실패했습니다.",
                                    Toast.LENGTH_SHORT).show();
                            //실패했을때 UI logic
                        }
                    }
                });
    }
*/

    private void signUp(){

        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입에 성공했습니다");
                                    //여기가 성공했을 때 UI 로직
                                } else {
                                    if(task.getException() != null){
                                        startToast(task.getException().toString());
                                    }
                                }//실패했을 때 UI 로직
                            }
                        });
            }
            else {
                startToast("비밀번호가 일치하지 않습니다");
            }
        }
        else{
            startToast("이메일 또는 비밀번호를 입력하십시오");
        }

    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /*private void StartLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    */
}