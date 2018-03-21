package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class register_activity extends AppCompatActivity {

    EditText correo,contraseña,rcontraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        correo = findViewById(R.id.correoid);
        contraseña = findViewById(R.id.contraseñaid);
        rcontraseña = findViewById(R.id.rcontraseñaid);

    }

    public void guardar(View view) {

        if (correo.getText().toString().equals("") || contraseña.getText().toString().equals("") || rcontraseña.getText().toString().equals("")) { //verifico que no hayan campos vacios
            Toast.makeText(this, "Faltan Datos", Toast.LENGTH_SHORT).show();
        }else {
            if(contraseña.getText().toString().equals(rcontraseña.getText().toString())) {
                Intent regeresomain = new Intent();
                regeresomain.putExtra("email", correo.getText().toString());          //esta parte es para devolverme al MainActivity y enviarle el correo y la contraseña
                regeresomain.putExtra("password", contraseña.getText().toString());
                setResult(RESULT_OK, regeresomain);
                finish();
            }else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
         }

    }
}
