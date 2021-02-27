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

import com.diu.farmerfriendsupport.Model.ModelUser;
import com.diu.farmerfriendsupport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText editTextfullName, editTextEmail, editTextPassword;
    private TextView textViewSignUp;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();

        textViewSignUp=(Button)findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(this);

        editTextfullName=(EditText) findViewById(R.id.signUpFullName);
        editTextEmail=(EditText) findViewById(R.id.signUpEmail);
        editTextPassword=(EditText) findViewById(R.id.signUpPassword);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignUp:
                registeruser();
                break;
        }

    }


    private void registeruser() {

        final String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        final String fullname=editTextfullName.getText().toString().trim();

        if(fullname.isEmpty()) {
            editTextfullName.setError("Full name is required");
            editTextfullName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            editTextEmail.setError("E-mail is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter valid email");
            editTextEmail.requestFocus();
            return;
        }


        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Password must be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            ModelUser user= new ModelUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),email,fullname,"admin","default");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this,"User has been registered!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignUp.this,"Failed to register!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }

                        else {
                            Toast.makeText(SignUp.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
}