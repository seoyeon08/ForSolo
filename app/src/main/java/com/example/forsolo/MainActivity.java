package com.example.forsolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.forsolo.findmate.activity.FindMateActivity;
import com.example.forsolo.groupBuying.groupBuyingActivity;
import com.example.forsolo.groupRecipe.groupRecipeActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView btn_FindMate;
    private ImageView btn_Findexchange;
    private ImageView btn_recipie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_FindMate = findViewById(R.id.btn_findMate);
        btn_FindMate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FindMateActivity.class);
                startActivity(intent);



            }


        });

        btn_Findexchange = findViewById(R.id.btn_findExchange);
        btn_Findexchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, groupBuyingActivity.class);
                startActivity(intent);



            }
        });

        btn_recipie = findViewById(R.id.btn_recipie);
        btn_recipie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, groupRecipeActivity.class);
                startActivity(intent);



            }
        });
    }


}