package com.example.forsolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewreviewActivity extends AppCompatActivity {

    Button Button_writereview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreview);

        Button_writereview = findViewById(R.id.Button_writereview);

        Button_writereview.setClickable(true);
        Button_writereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewreviewActivity.this, WritereviewActivity.class);
                startActivity(intent);
            }
        });

        }

    }


