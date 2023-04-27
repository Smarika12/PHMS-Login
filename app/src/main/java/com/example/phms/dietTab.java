package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dietTab extends AppCompatActivity
{
    FloatingActionButton fabMorning; // this is the main activity for diet
    RecyclerView recycleView;
    List<DataClass> dataList;


    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_tab);

        fabMorning = findViewById(R.id.breakfastFab);
        recycleView = findViewById(R.id.recyclerView1);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(dietTab.this, 1);
        recycleView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(dietTab.this);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();
        MyAdapterRecyclerView adapter = new MyAdapterRecyclerView(dietTab.this, dataList);
        recycleView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference phmsRef = databaseReference.child(uId).child("PHMS");
        dialog.show();

        valueEventListener = phmsRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                dataList.clear();
                for(DataSnapshot itemsnapshot : snapshot.getChildren())
                {
                    DataClass dataClass = itemsnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                dialog.dismiss();
            }
        });

        //going to the present main tab tot he upload thab
        fabMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(dietTab.this, uploadFoodCalorie.class));

            }
        });
    }
}