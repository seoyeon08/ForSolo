package com.example.forsolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.forsolo.findmate.activity.FindMateActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView btn_FindMate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_FindMate = findViewById(R.id.btn_findMate);
        btn_FindMate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FindMateActivity.class);
                startActivity(intent);
            }
        });
    }
}