package com.example.forsolo.findmate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forsolo.R;
import com.example.forsolo.WriteInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WritePostActivity extends AppCompatActivity {
    private static final String Tag = "WritePostActivity";

    TextView titleTextView;
    TextView timeTextView;
    TextView placeTextView;
    TextView memberCountTextView;

    EditText titleEditText;
    EditText timeEditText;
    EditText placeEditText;
    EditText memberEditText;
    EditText contentEditText;

    Button upload_btn;

    // EditText에서 가져온 데이텅
    String title, time, place, memberCount, content = null;

    private FirebaseUser user;
    private FirebaseFirestore fireStore;
    private FirebaseUser auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo);

        init();
        setting();

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if (title != null && time != null && place != null && memberCount != null && content != null) {
                    uploadData();
                } else {
                    Toast showText = Toast.makeText(getApplicationContext(), "내용을 다 채워주세요~", Toast.LENGTH_SHORT);
                    showText.show();
                }
            }

        });

    }


    private void init() {

        upload_btn = findViewById(R.id.upload_button);

        View titleLayout = findViewById(R.id.title_Layer);
        View timeLayout = findViewById(R.id.time_Layer);
        View placeLayout = findViewById(R.id.place_Layer);
        View memberLayout = findViewById(R.id.memberCount_Layer);

        titleTextView = titleLayout.findViewById(R.id.textView_Bord);
        timeTextView = timeLayout.findViewById(R.id.textView_Bord);
        placeTextView = placeLayout.findViewById(R.id.textView_Bord);
        memberCountTextView = memberLayout.findViewById(R.id.textView_Bord);

        titleEditText = titleLayout.findViewById(R.id.editText_Bord);
        timeEditText = timeLayout.findViewById(R.id.editText_Bord);
        placeEditText = placeLayout.findViewById(R.id.editText_Bord);
        memberEditText = memberLayout.findViewById(R.id.editText_Bord);
        contentEditText = findViewById(R.id.content_Edit);

    }


    // 요거는 인클루드된 뷰들의 텍스트를 바꾸는 구간
    private void setting() {
        titleTextView.setText("글 제목 :");
        timeTextView.setText("시간 :");
        placeTextView.setText("장소 :");
        memberCountTextView.setText("인원수 :");
    }

    // 업로드 버튼을 누르면 요 메서드가 딱! 호출되서 EditText의 데이터를 가져와서 변수에 저장하는 메서드
    private void getData() {

        title = titleEditText.getText().toString();
        time = timeEditText.getText().toString();
        place = placeEditText.getText().toString();
        memberCount = memberEditText.getText().toString();
        content = contentEditText.getText().toString();

    }

    // 본격적으로 FireStore 에 데이터를 올리는 부분
    private void uploadData() {
        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (auth != null) {
            email = auth.getEmail();
        }

        WriteInfo data = new WriteInfo(title, time, place, memberCount, content, email);

        fireStore.collection("solo_runch").document(title).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "업로드 성공!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                            finish();
                        }
                    }
                });
    }
}

