package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Actividad_principal extends AppCompatActivity {

    String correo="",contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            correo = extras.getString("usuario");  //obtengo los datos enviados de mainactivity
            contraseña = extras.getString("contraseña");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu); //para el menu de overflow
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //al seleccionar una opcion del menu de overflow

        int id = item.getItemId();

        if(id == R.id.perfilid){

            Intent intent = new Intent(this,perfil_activity.class);  //paso a la actividad perfil

            intent.putExtra("correo",correo);
            intent.putExtra("contraseña",contraseña);  //envio de datos
            startActivityForResult(intent,1234);    //espero respuesta de activity_perfil para saber si cierro sesion o me devuelvo a esta misma actividad

        }else if(id == R.id.csesionid){
            finish();           //cierro sesion, me devuelvo al login
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1234 && resultCode == RESULT_OK){    //esto es lo que lee la respuesta de activity_perfil
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
