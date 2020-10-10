package com.example.forsolo.findmate.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forsolo.R;
import com.example.forsolo.findmate.activity.SbordActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BordAdapter extends RecyclerView.Adapter<BordAdapter.itemViewHolder> {

    private ArrayList<BordInfo> listData = new ArrayList<>();
    private Context contexts;


    public void addData(BordInfo data) {
        listData.add(data);
    }

    public BordAdapter(Context context){
        contexts = context;
    }

    public void refresh(){
        listData.clear();
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bord_recyclerview_item, parent, false);

        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BordAdapter.itemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder {


        String title, time, place, memberCount, content, uploadTimeText, email, sc = null;

        private TextView titleTextView;
        private TextView subjectTextView;
        private TextView timeTextView;
        private View itemLayout;


        itemViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.bord_item_title);
            subjectTextView = itemView.findViewById(R.id.bord_item_subject);
            itemLayout = itemView.findViewById(R.id.bord_item_layout);
            timeTextView = itemView.findViewById(R.id.bord_item_time);

            itemClickListener();
        }

        private void itemClickListener() {

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contexts, SbordActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("time", time);
                    intent.putExtra("place", place);
                    intent.putExtra("memberCount", memberCount);
                    intent.putExtra("content", content);
                    intent.putExtra("email", email);
                    intent.putExtra("uploadTimeText", uploadTimeText);
                    intent.putExtra("sc", sc);
                    ContextCompat.startActivity(contexts, intent, new Bundle());
                }
            });
        }

        void onBind(BordInfo data) {

            titleTextView.setText(data.getTitle());
            subjectTextView.setText(data.getPlace());

            title = data.getTitle();
            time = data.getTime();
            place = data.getPlace();
            memberCount = data.getPerson();
            content = data.getContents();
            email = data.getEmail();
            uploadTimeText = data.getDate();
            sc = data.getSC();

            timeTextView.setText(uploadTimeText);

        }
    }
}
