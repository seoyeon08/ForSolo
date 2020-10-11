package com.example.forsolo.findmate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forsolo.R;
import com.example.forsolo.findmate.activity.WritePostActivity;
import com.example.forsolo.findmate.data.BordAdapter;
import com.example.forsolo.findmate.data.BordInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private View view;

    private BordAdapter bordAdapter;

    RecyclerView recyclerView;

    FloatingActionButton fab;


    public ArrayList<String> titleList = new ArrayList<>();
    public ArrayList<String> timeList = new ArrayList<>();
    public ArrayList<String> placeList = new ArrayList<>();
    public ArrayList<String> memberCountList = new ArrayList<>();
    public ArrayList<String> contentsList = new ArrayList<>();
    public ArrayList<String> emailList = new ArrayList<>();
    public ArrayList<String> dateList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerViewAction();
        fabAction();

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

    public void recyclerViewAction() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView = view.findViewById(R.id.Bord_RecyclerView);

        recyclerView.setLayoutManager(layoutManager);

        bordAdapter = new BordAdapter();


        recyclerView.setAdapter(bordAdapter);

    }

    public void getData() {
        // 데이터 받아오는 공간

        setData();
    }


    public void setData() {


        bordAdapter.setContextAdapter(getActivity());

        for (int i = 0; i < titleList.size(); i++) {

            BordInfo data = new BordInfo(titleList.get(i), timeList.get(i), placeList.get(i),
                    memberCountList.get(i), contentsList.get(i), emailList.get(i), dateList.get(i));

            bordAdapter.addData(data);
        }

        bordAdapter.notifyDataSetChanged();
    }


}
