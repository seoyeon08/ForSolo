package com.example.forsolo.findmate.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.forsolo.R;
import com.example.forsolo.chat.ChatActivity;
import com.example.forsolo.chat.ChattingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SbordActivity extends AppCompatActivity {

    Button chatButton, modify_btn, del_btn, update_btn;

    TextView name, date, place, time, member, content, title;

    EditText placeEdit, timeEdit, memberEdit, contentEdit, titleEdit;

    String titleText, timeText, placeText, memberCountText, contentText, uploadTimeText, emailText, sc, userName, userProfile = "";

    String titleUpdate, timeUpdate, placeUpdate, memberCountUpdate, contentUpdate, uploadTimeUpdate, emailUpdate = "";

    CircleImageView circleImageView;

    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_board);

        init();

        getData();

        onClickListener();

        transparency();

        checkUser();

        userSerVice();

    }

    private void transparency() {
        Drawable alpha = ((ImageView) findViewById(R.id.cute)).getDrawable();
        alpha.setAlpha(100);
    }

    private void init() {

        circleImageView = findViewById(R.id.userProfile_s);
        title = findViewById(R.id.s_title_textView);
        name = findViewById(R.id.userName_s);
        date = findViewById(R.id.dateCount);
        place = findViewById(R.id.placeText);
        time = findViewById(R.id.timeText);
        member = findViewById(R.id.memberCountText);
        content = findViewById(R.id.contentText);

        titleEdit = findViewById(R.id.titleEditText);
        placeEdit = findViewById(R.id.placeEditText);
        timeEdit = findViewById(R.id.timeEditText);
        memberEdit = findViewById(R.id.memberCountEditText);
        contentEdit = findViewById(R.id.contentEditText);

        chatButton = findViewById(R.id.chatButton);
        modify_btn = findViewById(R.id.modify_btn);
        del_btn = findViewById(R.id.del_btn);
        update_btn = findViewById(R.id.update_btn);
    }

    private void checkUser() {
        String nowUser;
        FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null) {
            nowUser = firebaseAuth.getEmail();
            if (nowUser != null && nowUser.equals(emailText)) {
                modify_btn.setVisibility(View.VISIBLE);
                del_btn.setVisibility(View.VISIBLE);
            }
        }
    }

    private void userSerVice() {

        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUI();

                updateData();

            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SbordActivity.this);
                builder.setTitle("게시물 삭제")
                        .setMessage("정말로 게시한 글을 삭제 하시겠습니까?");
                builder.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                fireStore = FirebaseFirestore.getInstance();

                                fireStore.collection("solo_runch").document(sc).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "삭제 완료!", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                    finish();
                                                    onBackPressed();
                                                }
                                            }
                                        });
                            }
                        });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });

    }

    private void updateUI() {

        modify_btn.setVisibility(View.GONE);
        del_btn.setVisibility(View.GONE);
        title.setVisibility(View.GONE);

        update_btn.setVisibility(View.VISIBLE);

        titleEdit.setVisibility(View.VISIBLE);
        timeEdit.setVisibility(View.VISIBLE);
        memberEdit.setVisibility(View.VISIBLE);
        placeEdit.setVisibility(View.VISIBLE);
        contentEdit.setVisibility(View.VISIBLE);

        titleEdit.setText(titleText);
        timeEdit.setText(timeText);
        memberEdit.setText(memberCountText);
        placeEdit.setText(placeText);
        contentEdit.setText(contentText);

        place.setText("장소 : ");
        time.setText("시간 : ");
        member.setText("인원 수 : ");
        content.setText("내용 : \n\n");

    }

    private void finishUI() {

        modify_btn.setVisibility(View.VISIBLE);
        del_btn.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

        update_btn.setVisibility(View.GONE);

        titleEdit.setVisibility(View.INVISIBLE);
        timeEdit.setVisibility(View.INVISIBLE);
        memberEdit.setVisibility(View.INVISIBLE);
        placeEdit.setVisibility(View.INVISIBLE);
        contentEdit.setVisibility(View.INVISIBLE);

    }

    private void updateData() {

        update_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                titleUpdate = titleEdit.getText().toString();
                placeUpdate = placeEdit.getText().toString();
                timeUpdate = timeEdit.getText().toString();
                memberCountUpdate = memberEdit.getText().toString();
                contentUpdate = contentEdit.getText().toString();

                Date uploadTime = new Date(System.currentTimeMillis());

                SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd HH:mm:ss");

                uploadTimeUpdate = mFormat.format(uploadTime);

                emailUpdate = emailText;

                if (!titleUpdate.equals("") && !placeUpdate.equals("") && !timeUpdate.equals("")
                        && !memberCountUpdate.equals("") && !contentUpdate.equals("") && emailUpdate != null) {

                    Log.d("asd", "asd");

                    Map<String, Object> upDateMap = new HashMap<>();
                    upDateMap.put("title", titleUpdate);
                    upDateMap.put("time", timeUpdate);
                    upDateMap.put("place", placeUpdate);
                    upDateMap.put("person", memberCountUpdate);
                    upDateMap.put("contents", contentUpdate);
                    upDateMap.put("email", emailUpdate);
                    upDateMap.put("date", uploadTimeUpdate);
                    upDateMap.put("sc", sc);

                    fireStore = FirebaseFirestore.getInstance();

                    fireStore.collection("solo_runch").document(sc).update(upDateMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    title.setText(titleUpdate);
                    time.setText("시간 : " + timeUpdate);
                    place.setText("장소 : " + placeUpdate);
                    member.setText("인원 수 : " + memberCountUpdate);
                    content.setText("내용 : \n\n" + contentUpdate);

                    date.setText(uploadTimeUpdate);

                    finishUI();
                } else {
                    Toast.makeText(getApplicationContext(), "빈 칸 없이 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
        sc = intent.getStringExtra("sc");
        userName = intent.getStringExtra("name");
        userProfile = intent.getStringExtra("profileUrl");

        setData();
    }

    @SuppressLint("SetTextI18n")
    private void setData() {

        // 프러필 완성시 여기에 데이터 주기
        name.setText(userName);

        if (userProfile != null){
            Glide.with(getApplicationContext()).load(userProfile).centerCrop().into(circleImageView);
        }
        // 정상 데이터
        title.setText(titleText);
        date.setText(uploadTimeText);
        place.setText("장소 : " + placeText);
        time.setText("시간 : " + timeText);
        member.setText("인원 수 : " + memberCountText);


        content.setText("내용 : \n\n" + contentText);
    }



    private void onClickListener() {
        // TODO: 1. 상대방을 지정하기 위해서 대화 상대의 정보를 지정함
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("roomCode", sc);
                intent.putExtra("roomTitle", titleText);
                startActivity(intent);
            }
        });
    }
}
