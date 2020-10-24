package com.example.forsolo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Tag;

public class UserFindActivity extends AppCompatActivity {

    private TextView btn_backLogin;
    private TextView btn_userfind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfind);

//        변수랑 버튼연결
        btn_userfind = findViewById(R.id.btn_userfind);
        btn_backLogin = findViewById(R.id.btn_backLogin);

        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserFindActivity.this, LoginActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });

        //비밀번호 찾기 버튼에다가 기능연결
        btn_userfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_userfind:
                        send();     //point
                        break;
            }
            }
        });

    }

    private void send(){
        //이메일 입력정보 가져오기
        String email = ((EditText)findViewById(R.id.email_userfind)).getText().toString();

        if(email.length() > 0){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startToast("이메일을 보냈습니다");
                            }
                        }
                    });
        }
        else{
            startToast("이메일을 입력해주세요");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
