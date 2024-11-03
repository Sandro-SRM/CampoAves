package com.example.campoaves;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView rTextViewRegister;
    TextInputEditText rtviemail;
    TextInputEditText rtvipassword;
    Button rbtnlogin;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rTextViewRegister = findViewById(R.id.TextViewRegister);
        rtviemail = findViewById(R.id.tviemail);
        rtvipassword = findViewById(R.id.tvipassword);
        rbtnlogin = findViewById(R.id.btnlogin);

        mAuth = FirebaseAuth.getInstance();



        rTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        rbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String correo = rtviemail.getText().toString();
        String contra = rtvipassword.getText().toString();

        mAuth.signInWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "El Email y La Contraseña no son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        Log.d("Campo", "email"+correo);
        Log.d("Campo", "password"+contra);
    }
}