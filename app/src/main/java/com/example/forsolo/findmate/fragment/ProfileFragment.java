package com.example.forsolo.findmate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.forsolo.LoginActivity;
import com.example.forsolo.ManagementData;
import com.example.forsolo.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize();

        return root;
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
    private void logOut () {
        firebaseAuth.getInstance().signOut();
    }

    private void signOut () {
        firebaseAuth.getCurrentUser().delete();
    }
}
