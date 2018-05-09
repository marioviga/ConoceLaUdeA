package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Actividad_principal extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    String correo="",contraseña;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            //correo = extras.getString("usuario");  //obtengo los datos enviados de mainactivity
            a = extras.getInt("inicio_con");
        }

        inicializar();
    }

    private void inicializar(){

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Log.d("firebaseuser","usuario logueado : "+firebaseUser.getDisplayName());
                    Log.d("firebaseuser","usuario logueado : "+firebaseUser.getEmail());
                    correo = firebaseUser.getEmail();
                    //Picasso.get().load(firebaseUser.getPhotoUrl()).into(iFoto);
                }else{
                    Log.d("firebaseuser","cesion cerrada por el usuario");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
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

            intent.putExtra("correo",correo);  //envio de datos
            intent.putExtra("inicio_con",a);
            startActivityForResult(intent,1234);    //espero respuesta de activity_perfil para saber si cierro sesion o me devuelvo a esta misma actividad

        }else if(id == R.id.csesionid){
            switch (a) {
                case 1:
                    firebaseAuth.signOut();
                    finish();
                    break;
                case 2:
                    cerrarsesiongoogle();
                    finish();
                    break;
                case 3:
                    LoginManager.getInstance().logOut();
                    finish();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void cerrarsesiongoogle(){

        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    finish();  //cierro sesion, me devuelvo al login
                    Toast.makeText(Actividad_principal.this,"Cesion cerrada",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Actividad_principal.this,"Error cerrando cesion con Google",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1234 && resultCode == RESULT_OK){    //esto es lo que lee la respuesta de activity_perfil
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
        googleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
