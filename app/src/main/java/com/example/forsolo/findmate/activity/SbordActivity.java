package com.example.forsolo.findmate.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forsolo.R;

public class SbordActivity extends AppCompatActivity {

    Button chatButton;

    TextView name, date, place, time, member, content, title;

    String titleText, timeText, placeText, memberCountText, contentText, uploadTimeText, emailText = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_board);

        init();
        getData();
        onClickListener();
    }

    private void init() {

        title = findViewById(R.id.s_title_textView);
        name = findViewById(R.id.userName_s);
        date = findViewById(R.id.dateCount);
        place = findViewById(R.id.placeText);
        time = findViewById(R.id.timeText);
        member = findViewById(R.id.memberCountText);
        content = findViewById(R.id.contentText);

        chatButton = findViewById(R.id.chatButton);
    }

    private void getData() {

        Intent intent = getIntent();

        titleText = intent.getStringExtra("title");
        timeText = intent.getStringExtra("time");
        placeText = intent.getStringExtra("place");
        memberCountText = intent.getStringExtra("memberCount");
        contentText = intent.getStringExtra("content");
        uploadTimeText = intent.getStringExtra("uploadTimeText");
        emailText = intent.getStringExtra("email");

        setData();
    }

    @SuppressLint("SetTextI18n")
    private void setData() {

        // 프러필 완성시 여기에 데이터 주기
        name.setText("이름");

        // 정상 데이터
        title.setText(titleText);
        date.setText(uploadTimeText);
        place.setText("장소 : " + titleText);
        time.setText("시간 : " + timeText);
        member.setText("인원 수 : " + memberCountText);
        content.setText("내용 : \n" + contentText);
    }

    private void onClickListener(){
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Chat 연동 필요

            }
        });
    }
}
