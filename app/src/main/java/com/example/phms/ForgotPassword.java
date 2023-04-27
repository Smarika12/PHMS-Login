package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity
{
    EditText mforgotpassword;
    Button mpasswordRecoverButton;
    TextView mgoBackToLogin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);



        mforgotpassword = findViewById(R.id.forgotPassword);
        mpasswordRecoverButton = findViewById(R.id.passordRecoverButton);
        mgoBackToLogin = findViewById(R.id.goBackToLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        mgoBackToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ForgotPassword.this, LogIn.class));

            }
        });

        mpasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = mforgotpassword.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter your email Please!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // firebase code for sending verification
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Email is sent to your existing account, You can recover your password using email", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, LogIn.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "The provided email doesnot exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        });
    }
}