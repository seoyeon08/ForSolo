package com.example.forsolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity{

    private Button btn_backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btn_backLogin = findViewById(R.id.btn_backLogin);
        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this, LoginActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });

    }
}
