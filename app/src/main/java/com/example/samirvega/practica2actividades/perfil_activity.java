package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

public class perfil_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    String correo, contraseña;
    TextView email,password;
    int a;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    private ImageView iFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_activity);

        email = findViewById(R.id.infocorreoid);
        password = findViewById(R.id.infocontraseñaid);
        iFoto = findViewById(R.id.iFoto);

        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            correo = extras.getString("correo");  //obtengo los datos , me sale null no se porque
            a = extras.getInt("inicio_con");
        }
        inicializar();

        email.setText(correo);
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
                    Picasso.get().load(firebaseUser.getPhotoUrl()).into(iFoto);
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
                    Toast.makeText(perfil_activity.this,"Cesion cerrada",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(perfil_activity.this,"Error cerrando cesion con Google",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

}
