package com.example.forsolo.findmate.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.forsolo.LoadingActivity;
import com.example.forsolo.LoginActivity;
import com.example.forsolo.ManagementData;
import com.example.forsolo.R;
import com.example.forsolo.findmate.activity.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private FirebaseAuth firebaseAuth;
    private View root;
    Button write_btn;
    private ImageView profileImageView;
    private TextView  nameTextView;
    private TextView  ageTextView;
    private TextView  majorTextView;
    private TextView  introTextView;
    private TextView  genderTextView;
    private Context context;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize();

        write_btn=root.findViewById(R.id.writeButton);
        profileImageView = root.findViewById(R.id.profileImageView);
        nameTextView = root.findViewById(R.id.nameTextView);
        ageTextView = root.findViewById(R.id.ageTextView);
        majorTextView = root.findViewById(R.id.majorTextView);
        introTextView = root.findViewById(R.id.profile_intro_TextView);
        genderTextView=root.findViewById(R.id.genderTextView);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void initialize() {
        Button btnLogOut, btnSignOut;

        btnLogOut = root.findViewById(R.id.btnLogOut);
        btnSignOut = root.findViewById(R.id.btnSignOut);    //btnSignOut을 연결해준다.

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                Intent intent = new Intent(
                        getContext(), LoginActivity.class);

                // 데이터 초기화 및 생성
                ManagementData.getInstance().delAllData();

                startActivity(intent);

            }
        });


        //회원탈퇴를 하는 과정
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_signOut();
            }
        });
    }

    //회원탈퇴 메소드
    public void onClick_signOut(){
        new AlertDialog.Builder(getContext())
                .setTitle("회원 탈퇴")
                .setMessage("정말로 탈퇴 하시겠습니까?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 확인시 처리 로직
                        Intent intent = new Intent(getContext(), LoadingActivity.class);
                        // 데이터 초기화 및 생성
                        ManagementData.getInstance().delAllData();

                        startActivity(intent);

                        signOut();

                        Toast.makeText(getActivity(),"탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 취소시 처리 로직
                        Toast.makeText(getActivity(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }})
                .show();
    }

    private void loadData() {

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            if(document.getData().get("photoUrl") != null){
                                Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileImageView);
                            }
                            nameTextView.setText(document.getData().get("name").toString());
                            ageTextView.setText(document.getData().get("age").toString());
                            majorTextView.setText(document.getData().get("major").toString());
                            introTextView.setText(document.getData().get("intro").toString());
                            genderTextView.setText(document.getData().get("gender").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void logOut() {
        firebaseAuth.getInstance().signOut();
    }

    //회원 탈퇴부분
    private void signOut() {
        firebaseAuth.getCurrentUser().delete(); }


    @Override
    public void onResume() {
        super.onResume();
        // TODO: onResume은 화면이 다시 열릴때 실행됩니다. 최초 화면 실행시 onCreateView 다음으로 실행됩니다.
        loadData();
    }
}
