package com.example.campoaves;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView rimageback;
    TextInputEditText rtvinombreusuario;
    TextInputEditText rtvicorreoregistro;
    TextInputEditText rtvicontraseña;
    TextInputEditText rtviconfirmacontra;
    Button rbtnregistrarse;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rimageback = findViewById(R.id.imageback);
        rtvinombreusuario = findViewById(R.id.tvinombreusuario);
        rtvicorreoregistro = findViewById(R.id.tvicorreoregistro);
        rtvicontraseña = findViewById(R.id.tvicontraseña);
        rtviconfirmacontra = findViewById(R.id.tviconfirmacontra);
        rbtnregistrarse = findViewById(R.id.btnregistrarse);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        rimageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rbtnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro();
            }
        });
    }

    private void registro() {
        String usuario = rtvinombreusuario.getText().toString();
        String correo = rtvicorreoregistro.getText().toString();
        String contra = rtvicontraseña.getText().toString();
        String confirmarcontra = rtviconfirmacontra.getText().toString();

        if (!usuario.isEmpty() && !correo.isEmpty() && !contra.isEmpty() && !confirmarcontra.isEmpty()){
            if (isEmailValid(correo)){
                if (contra.equals(confirmarcontra)){
                    if (contra.length() >=6){
                        crearUsuario(correo,contra,usuario);
                        Log.i("Infox", correo);
                        Log.i("Infox", contra);
                    }else {
                        Toast.makeText(this, "Las contrasñas debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }


            } else {

            Toast.makeText(this, "Has insertado todos los campos pero el correo no es valido", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void crearUsuario(String correo, String contra, String usuario) {
        mAuth.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id=mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("correo", correo);
                    map.put("usuario", usuario);
                    map.put("contraseña", contra);
                    mFirestore.collection("Usuarios").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "El Usuario se almaceno correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "El Usuario No se pudo almacenar correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    Toast.makeText(RegisterActivity.this, "El Usuario se registro correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isEmailValid (String correo) {
        String expression = "^[\\w\\.-]+@([\\w\\.-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}