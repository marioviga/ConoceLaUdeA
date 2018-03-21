package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText correo,contraseña;
    String scorreo="",scontraseña="";
    boolean cr,cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.eUsuario);
        contraseña = findViewById(R.id.econtraseña);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    // estas funciones hay que crearlas , no estoy seguro porque pero hay que hacerlo
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override   // esta funcion se activa cuando me envian datos de register_activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          //aqui confirmamos que esta respuesta si sea de register_activity ( mire la linea 78)
        if(requestCode==123 && resultCode==RESULT_OK){

            if(data.getExtras()!=null) {
                scorreo = data.getExtras().getString("email");  //obtenemos los datos de la respuesta de register
                scontraseña = data.getExtras().getString("password");
            }

        }else Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void registrarse(View view) {  // con esta funcion pasamos a register_activity(se activa si le da click en registro)

        Intent regis = new Intent(this,register_activity.class);
        startActivityForResult(regis,123); //se pone asi porque se va a esperar una respuesta, sino esperamos nada seria  solo startActivity
    }

    public void Ingresar(View view) {  // se activa al presionar ingresar

        cn=false;
        cr=false;

        if (correo.getText().toString().equals("") || contraseña.getText().toString().equals("")) { //verifico que no hayan campos vacios
            Toast.makeText(this, "Faltan Datos", Toast.LENGTH_SHORT).show();
        } else {

            if (correo.getText().toString().equals(scorreo)) {
                cr = true;                                      //verifico que los datos si correspondan
                if (contraseña.getText().toString().equals(scontraseña)) {
                    cn = true;
                } else Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
        if(cr && cn){  // solo pongo cn,cr y el condicional lo lee como true
            Intent ingreso = new Intent(this,Actividad_principal.class);   // si los datos estan correctos paso a Actividad_principal
            ingreso.putExtra("usuario",scorreo);
            ingreso.putExtra("contraseña",scontraseña);     //envio los valores de usuario y contraseña a la clase Actividad_principal
            startActivity(ingreso);
        }
    }
}
