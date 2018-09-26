package com.andromeeda.project.firebaselogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    EditText mail, pswd;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) { // If already logged in
//            startActivity(new Intent(MainActivity.this, Home.class));
//            finish();
//        }

        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login_login);
        signup = findViewById(R.id.login_signup);
        mail = findViewById(R.id.login_email);
        pswd = findViewById(R.id.login_pswd);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                final String password = pswd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(!task.isSuccessful()){
                                    if(password.length() < 6){
                                        pswd.setError("Password too short, enter minimum 6 characters!");
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this, Home.class));
                                }
                            }
                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
