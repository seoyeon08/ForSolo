package com.example.forsolo.groupBuying;

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

import com.bumptech.glide.Glide;
import com.example.forsolo.R;
import com.example.forsolo.groupBuying.GbordActivity;
import com.example.forsolo.groupBuying.GBordInfo;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class GBordAdapter extends RecyclerView.Adapter<GBordAdapter.itemViewHolder> {

    private ArrayList<GBordInfo> listData = new ArrayList<>();
    private ArrayList<GBordInfo> arrayList;
    private Context contexts;

    public void addData(GBordInfo data) {
        listData.add(data);
    }

    public GBordAdapter(Context context) {
        contexts = context;
    }

    public void refresh() {
        listData.clear();
    }

    public void setList() {
        arrayList = new ArrayList<>();
        arrayList.addAll(listData);
    }

    public void filter(String searchText) {
        searchText = searchText.toLowerCase(Locale.getDefault());
        listData.clear();
        if (searchText.length() == 0) {
            if (arrayList != null) {
                listData.addAll(arrayList);
            }
        } else {
            if (arrayList != null) {
                for (GBordInfo bordInfo : arrayList) {
                    String name = bordInfo.getTitle();
                    if (name.toLowerCase().contains(searchText)) {
                        listData.add(bordInfo);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bord_recyclerview_item, parent, false);

        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GBordAdapter.itemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {


        String title, time, place, memberCount, content, uploadTimeText, email, sc, userName, userProfileUrl = null;

        private TextView titleTextView;
        private TextView subjectTextView;
        private TextView timeTextView;
        private View itemLayout;
        private CircleImageView circleImageView;


        itemViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.bord_item_title);
            subjectTextView = itemView.findViewById(R.id.bord_item_subject);
            itemLayout = itemView.findViewById(R.id.bord_item_layout);
            timeTextView = itemView.findViewById(R.id.bord_item_time);
            circleImageView = itemView.findViewById(R.id.bord_item_Image);

            itemClickListener();
        }

        private void itemClickListener() {

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contexts, GbordActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("time", time);
                    intent.putExtra("place", place);
                    intent.putExtra("memberCount", memberCount);
                    intent.putExtra("content", content);
                    intent.putExtra("email", email);
                    intent.putExtra("uploadTimeText", uploadTimeText);
                    intent.putExtra("sc", sc);
                    intent.putExtra("name", userName);
                    intent.putExtra("profileUrl", userProfileUrl);
                    ContextCompat.startActivity(contexts, intent, new Bundle());
                }
            });
        }

        void onBind(GBordInfo data) {

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

            userName = data.getUserName();
            userProfileUrl = data.getUserProfileUrl();

            if (userProfileUrl != null){
                Glide.with(contexts).load(userProfileUrl).into(circleImageView);
            }
        }
    }
}
