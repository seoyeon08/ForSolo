package com.example.forsolo.findmate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.forsolo.LoginActivity;
import com.example.forsolo.ManagementData;
import com.example.forsolo.R;
import com.example.forsolo.ReportActivity;
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
        final ImageView profileImageView = root.findViewById(R.id.profileImageView);
        final TextView nameTextView = root.findViewById(R.id.nameTextView);
        final TextView ageTextView = root.findViewById(R.id.ageTextView);
        final TextView majorTextView = root.findViewById(R.id.majorTextView);
        final TextView introTextView = root.findViewById(R.id.profile_intro_TextView);

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

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
                                    ageTextView.setText(document.getData().get("phoneNumber").toString());
                                    majorTextView.setText(document.getData().get("birthDay").toString());
                                    introTextView.setText(document.getData().get("address").toString());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
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
        btnSignOut = root.findViewById(R.id.btnSignOut);

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

        btnSignOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                getContext(), LoginActivity.class);

                        // 데이터 초기화 및 생성
                        ManagementData.getInstance().delAllData();

                        startActivity(intent);

                        signOut();
                    }
                });
            }

            private void logOut() {
                firebaseAuth.getInstance().signOut();
            }

            private void signOut() {
                firebaseAuth.getCurrentUser().delete();
            }
}
