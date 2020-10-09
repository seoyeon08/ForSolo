package com.example.forsolo.findmate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forsolo.R;
import com.example.forsolo.findmate.activity.WritePostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListFragment extends Fragment {

    private View view;

    FloatingActionButton fab;
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        fabAction();
        recyclerViewAction();
        return view;
    }

    public void fabAction() {
        fab = view.findViewById(R.id.write_text);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WritePostActivity.class);
                startActivity(intent);
            }
        });
    }

    public void recyclerViewAction(){
        recyclerView = view.findViewById(R.id.Bord_RecyclerView);

    }

}
