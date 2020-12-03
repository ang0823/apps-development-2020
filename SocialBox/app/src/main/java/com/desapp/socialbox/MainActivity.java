package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
    }

    public void iniciarSesion(View vista){
        Intent actividad = new Intent(this, NavigationActivity.class);
        startActivity(actividad);
        this.finish();
    }

    public void crearCuenta(View vista){
        Intent actividad = new Intent(this, SignupActivity.class);
        startActivity(actividad);
        this.finish();
    }
}