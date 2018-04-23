package com.example.samirvega.practica2actividades;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.Auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private static final int LOGIN_CON_GOOGLE = 1;
    private GoogleApiClient googleApiClient;
    private SignInButton btnSignInGoogle;
    EditText correo,contraseña;
    String scorreo="",scontraseña="";
    boolean cr,cn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);
        correo = findViewById(R.id.eUsuario);
        contraseña = findViewById(R.id.econtraseña);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login facebook","ok");
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("login facebook","cancelado por usuario");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("login facebook","error");
            }
        });
        getHashes();
        inicializar();
        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "presionado", Toast.LENGTH_SHORT).show();
                Log.d("googleboton","boton presionado");
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,LOGIN_CON_GOOGLE);
            }
        });


    }

    private void signInFacebook(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.
                getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Cuenta creada",Toast.LENGTH_SHORT).show();
                            cr = true;
                            cn = true;
                            Log.d("FirebaseUser","true");
                            goprincipal();

                        }else{
                            Toast.makeText(MainActivity.this, "Error al crear"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }*/

    private void getHashes() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.samirvega.practica2actividades",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void inicializar(){
        firebaseAuth=FirebaseAuth.getInstance(); //para conectar firebase
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Log.d("FirebaseUser","Usuario logueado "+firebaseUser.getEmail());
                }else{
                    Log.d("FirebaseUser","No hay usuario logueado ");
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

        }else if(requestCode==LOGIN_CON_GOOGLE){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInGoogle(googleSignInResult);

        }else{
                callbackManager.onActivityResult(requestCode,resultCode,data);
                }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void signInGoogle (GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(), null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            goprincipal();
                        }
                    });
        }
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

            firebaseAuth.signInWithEmailAndPassword(correo.getText().toString(),contraseña.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        cr = true;
                        cn = true;
                        Log.d("FirebaseUser","true");
                        goprincipal();

                    }else{
                        Toast.makeText(MainActivity.this, "Error al crear"+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }

            });

            /*if (correo.getText().toString().equals(scorreo)) {
                cr = true;                                      //verifico que los datos si correspondan
                if (contraseña.getText().toString().equals(scontraseña)) {
                    cn = true;
                } else Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();*/
        }
    }

    public void goprincipal(){

            Log.d("FirebaseUser", "xxxxxxxxxxxxxxxxx ");
            Intent ingreso = new Intent(this, Actividad_principal.class);   // si los datos estan correctos paso a Actividad_principal
            //ingreso.putExtra("usuario",scorreo);
            //ingreso.putExtra("contraseña",scontraseña);     //envio los valores de usuario y contraseña a la clase Actividad_principal
            startActivity(ingreso);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
