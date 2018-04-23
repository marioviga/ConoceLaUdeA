package com.example.samirvega.practica2actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.samirvega.practica2actividades.modelos.Usuarios;

public class PruebaActivity extends AppCompatActivity {
    private EditText nombre,correo,telefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
    }

    public void guardar(View view) {


    }
}
