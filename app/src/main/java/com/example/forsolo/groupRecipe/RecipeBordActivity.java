package com.example.forsolo.groupRecipe;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class RecipeBordActivity extends AppCompatActivity {

    Button chatButton, modify_btn, del_btn, update_btn;

    TextView name, date, place, time, content, title;

    EditText timeEdit, placeEdit, contentEdit, titleEdit;

    String titleText, timeText,  placeText,  contentText, uploadTimeText, emailText, sc, userName, userProfile = "";

    String titleUpdate, timeUpdate,  placeUpdate, contentUpdate, uploadTimeUpdate, emailUpdate = "";

    CircleImageView circleImageView;

    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_board);

        init();

        getData();

//        onClickListener();

        transparency();

        checkUser();

        userSerVice();

    }

    private void transparency() {
        Drawable alpha = ((ImageView) findViewById(R.id.cute)).getDrawable();
        alpha.setAlpha(100);
    }

    //초기화
    private void init() {

        circleImageView = findViewById(R.id.x_userProfile_s);
        title = findViewById(R.id.x_title_textView);
        name = findViewById(R.id.x_userName_s);
        date = findViewById(R.id.x_dateCount);
        place = findViewById(R.id.x_placeText);
        time = findViewById(R.id.g_timeText);
        content = findViewById(R.id.x_contentText);

        titleEdit = findViewById(R.id.x_titleEditText);
        placeEdit = findViewById(R.id.x_placeEditText);
        timeEdit = findViewById(R.id.x_timeEditText);
        contentEdit = findViewById(R.id.x_contentEditText);

        chatButton = findViewById(R.id.x_chatButton);
        modify_btn = findViewById(R.id.x_modify_btn);
        del_btn = findViewById(R.id.x_del_btn);
        update_btn = findViewById(R.id.x_update_btn);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeBordActivity.this);
                builder.setTitle("게시물 삭제")
                        .setMessage("정말로 게시한 글을 삭제 하시겠습니까?");
                builder.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                fireStore = FirebaseFirestore.getInstance();

                                fireStore.collection("recipe").document(sc).delete()
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
        placeEdit.setVisibility(View.VISIBLE);
        contentEdit.setVisibility(View.VISIBLE);

        titleEdit.setText(titleText);
        timeEdit.setText(timeText);
        placeEdit.setText(placeText);
        contentEdit.setText(contentText);


        time.setText("한줄 소개 : ");
        place.setText("메뉴명 : ");
        content.setText("요리 순서 : \n\n");

    }

    private void finishUI() {

        modify_btn.setVisibility(View.VISIBLE);
        del_btn.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

        update_btn.setVisibility(View.GONE);

        titleEdit.setVisibility(View.INVISIBLE);
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
                contentUpdate = contentEdit.getText().toString();

                Date uploadTime = new Date(System.currentTimeMillis());

                SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd HH:mm:ss");

                uploadTimeUpdate = mFormat.format(uploadTime);

                emailUpdate = emailText;

                if (!titleUpdate.equals("") && !placeUpdate.equals("") && !contentUpdate.equals("") && emailUpdate != null) {

                    Log.d("asd", "asd");

                    Map<String, Object> upDateMap = new HashMap<>();
                    upDateMap.put("title", titleUpdate);
                    upDateMap.put("time", timeUpdate);
                    upDateMap.put("place", placeUpdate);
                    upDateMap.put("contents", contentUpdate);
                    upDateMap.put("email", emailUpdate);
                    upDateMap.put("date", uploadTimeUpdate);
                    upDateMap.put("sc", sc);

                    fireStore = FirebaseFirestore.getInstance();

                    fireStore.collection("recipe").document(sc).update(upDateMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    title.setText(titleUpdate);
                    time.setText("한줄 소개 : " + timeUpdate);
                    place.setText("메뉴명 : " + placeUpdate);
                    content.setText("요리 순서 : \n\n" + contentUpdate);

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
        place.setText("메뉴명 : " + placeText);
        time.setText("한줄 소개 : " + timeText);
        content.setText("요리 순서 : \n\n" + contentText);
    }



//    private void onClickListener() {
//        chatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
//                intent.putExtra("email", emailText);
//                startActivity(intent);
//            }
//        });
//    }
}
