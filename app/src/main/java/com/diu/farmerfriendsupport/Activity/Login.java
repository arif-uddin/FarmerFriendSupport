package com.diu.farmerfriendsupport.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diu.farmerfriendsupport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView ctxtSignUpHere,forgetPassword;
    EditText LoginEmail, LoginPassword;
    Button btnLogin;
    ProgressBar progressBarLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ctxtSignUpHere=(TextView) findViewById(R.id.ctxtSignUpHere);
        forgetPassword=(TextView) findViewById(R.id.textViewForgetPassword);

        ctxtSignUpHere.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);

        LoginEmail=(EditText) findViewById(R.id.editTextLoginEmail);
        LoginPassword=(EditText) findViewById(R.id.editTextLoginPassword);

        progressBarLogin=(ProgressBar) findViewById(R.id.loginProgressBar);

        btnLogin=(Button) findViewById(R.id.btnLogin);

        mAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ctxtSignUpHere:
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.textViewForgetPassword:
                Intent newintent = new Intent(Login.this, ForgetPassword.class);
                startActivity(newintent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void login() {
        String emailLogin=LoginEmail.getText().toString().trim();
        String passwordLogin=LoginPassword.getText().toString().trim();

        if(emailLogin.isEmpty()) {
            LoginEmail.setError("E-mail is required");
            LoginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailLogin).matches()) {
            LoginEmail.setError("Please enter valid email");
            LoginEmail.requestFocus();
            return;
        }

        if(passwordLogin.isEmpty()) {
            LoginPassword.setError("Password is required");
            LoginPassword.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null)
                            {   progressBarLogin.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            //updateUI(user);
                        } else {
                            progressBarLogin.setVisibility(View.GONE);
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }
}