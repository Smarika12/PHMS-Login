package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity
{
    EditText userName;
    EditText password, mail;
    Button buttonXML;
    TextView registration;
    TextView forgotPassword;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userName = findViewById(R.id.editTextUserName);
        buttonXML = findViewById(R.id.button);
        password =  findViewById(R.id.editTextPassword);
        mail = findViewById(R.id.emailAddress);

        registration = findViewById(R.id. textViewRegistration);
        forgotPassword = findViewById(R.id.editTextForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        /*if (firebaseUser != null)
        {
            finish();
            startActivity(new Intent(LogIn.this, HomeMenu.class));
        }*/


        buttonXML.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String user = userName.getText().toString(); //getting username from user
                String pass = password.getText().toString(); //getting password from user
                String email = mail.getText().toString();
                Database db = new Database(getApplicationContext(), "PHMS", null, 1);

                if (user.length() == 0 || pass.length() == 0 || email.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please insert your correct username, email and password to login!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //logging in the user
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                checkMailVerification();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Account doesn't exist!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                  /*  if (db.login(user, pass) == 1)
                    {
                        /////////////////////
                        //Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user);
                        editor.apply();
                        //startActivity(new Intent(LogIn.this, HomeMenu.class));
                  }*/ //giving error
                }
            }
        });

        //onclicklistener to going to the registration page
        registration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LogIn.this, registration.class));
            }
        });
        //onclicklistener to going from login to the forgot password page
        forgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LogIn.this, ForgotPassword.class));
            }
        });

    }

    public void checkMailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified() == true)
        {
            Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LogIn.this, HomeMenu.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Verify your account first!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();

        }
    }
}