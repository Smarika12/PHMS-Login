package com.example.phms;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class uploadFoodCalorie extends AppCompatActivity
{
    Button saveButton;
    EditText food, calorie;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_food_calorie);

        saveButton = findViewById(R.id.addIcon);
        food = findViewById(R.id.uploadFoodName);
        calorie = findViewById(R.id.uploadFoodCalorie);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//
//                    }
//                }
//        )
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //pending
                uploadData();
            }
        });
    }

    public void uploadData()
    {
        String strfood = food.getText().toString();
        String strCalorie = calorie.getText().toString();
        String uId = firebaseAuth.getCurrentUser().getUid();  // new code

        DataClass dataClass= new DataClass(strfood, strCalorie);

        FirebaseDatabase.getInstance().getReference("users").child(uId).child("PHMS").child(strfood)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(uploadFoodCalorie.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(uploadFoodCalorie.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

}