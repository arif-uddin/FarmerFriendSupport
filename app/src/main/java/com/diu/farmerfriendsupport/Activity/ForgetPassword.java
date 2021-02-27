package com.diu.farmerfriendsupport.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diu.farmerfriendsupport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnPassworsdReset;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        mAuth=FirebaseAuth.getInstance();

        editTextEmail=(EditText) findViewById(R.id.editTextPasswordReset);

        btnPassworsdReset=(Button) findViewById(R.id.btnResetLink);
        btnPassworsdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(editTextEmail.getText().toString().trim().replaceAll(" ",""))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPassword.this, "Check your email to reset password!", Toast.LENGTH_SHORT).show();
                                    //go to login page
                                    Intent intent = new Intent(ForgetPassword.this,Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        });
            }
        });
    }
}