package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class registration extends AppCompatActivity
{
    EditText username, email, password, confirmPass;
    Button btn;
    TextView textView; //textview is going from the registration to the login page

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        username = findViewById(R.id.xmlRegUserName);
        email= findViewById(R.id.editTextUserEmailAddress);
        confirmPass = findViewById(R.id.editTextConfirmPassword);
        password = findViewById(R.id.RegPassword);

        firebaseAuth = FirebaseAuth.getInstance(); // storing instance to firebase auth

        btn = findViewById(R.id.button);
        textView = findViewById(R.id.textViewOldUSER);

        //going from the registration to the login
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(registration.this, LogIn.class));
            }
        });


        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name, mail, pass, cnfrmPass;
                name = username.getText().toString();
                mail = email.getText().toString();
                pass = password.getText().toString();
                cnfrmPass = confirmPass.getText().toString();


                Database database = new Database(getApplicationContext(), "PHMS", null, 1);

                if (name.length()==0 || mail.length() == 0 || pass.length() == 0 || cnfrmPass.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please fill in your name on the box", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (pass.compareTo(cnfrmPass) == 0)
                    {
                        if (isValid(pass)) //password atleast 8 digit is true so register to firebase and data base
                        {
                            //if the password is valid then save the record to the data base
//     (giving error)                       database.register(name, mail, pass); //storing the name, email, pass to my database
                            //below we are also creating our database in firebase just for safety
                            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        sendEmailVerification();
                                    }
                                }
                            });
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registration.this, LogIn.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Password must contain atleast 8 characters with letter, digit and special character ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Password and confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //checking for valid password which should be atleast 8 characters
    public static boolean isValid(String passwordOHere)
    {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordOHere.length() < 8)
        {
            return false; // passoword atleast 8
        }
        else
        {
            for (int p = 0; p < passwordOHere.length(); p++)
            {
                if (Character.isLetter(passwordOHere.charAt(p)))
                {
                    f1 = 1; // a letter
                }
            }
            for (int r = 0; r < passwordOHere.length(); r++)
            {
                if (Character.isDigit(passwordOHere.charAt(r)))
                {
                    f2 = 1 ; // digit
                }
            }
            for (int s = 0; s < passwordOHere.length(); s++)
            {
                char c = passwordOHere.charAt(s); // one character
                if (c >= 33 && c <= 46 || c == 64)
                {
                    f3 = 1;
                }
            }
            if (f1 ==1 && f2 == 1 && f3 == 1)
            {
                return true;
            }
            return false;
        }
    }

    public void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    Toast.makeText(getApplicationContext(), "Verification Email is sent\nVerify and login Again!", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    //startActivity(new Intent(registration.this, LogIn.class));
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Failed to send Verification Email", Toast.LENGTH_SHORT).show();
        }

    }
}