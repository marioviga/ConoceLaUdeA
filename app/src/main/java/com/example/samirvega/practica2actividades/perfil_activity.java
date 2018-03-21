package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class perfil_activity extends AppCompatActivity {

    String correo, contraseña;
    TextView email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_activity);

        email = findViewById(R.id.infocorreoid);
        password = findViewById(R.id.infocontraseñaid);

        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            correo = extras.getString("correo");  //obtengo los datos , me sale null no se porque
            contraseña = extras.getString("contraseña");
        }


        email.setText(correo);
        password.setText(contraseña);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  //menu de overflow
      getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //item seleccionado del menu
        int id = item.getItemId();

        if(id == R.id.principalid){
            finish();           //para devolverme a Actividad_principal
        }else if(id == R.id.csesion2id){

            Intent intent = new Intent();
            setResult(RESULT_OK,intent);  // para cerrar sesion e irse al login, lo que hago es enviar la respuesta a Activity_main y en la funcion que implemente (onActivityResult) se cierra Actividad_principal
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
