package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        CardView notesCard = findViewById(R.id.notes);
        notesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeMenu.this, notesTab.class));
            }
        });

        //going inside the diet tab now
        CardView goToDiet = findViewById(R.id.diet);
        goToDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeMenu.this, dietTab.class));
            }
        });

    }
}