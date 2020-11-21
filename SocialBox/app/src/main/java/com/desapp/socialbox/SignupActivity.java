package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void cancelarRegistro(View view){
        Intent actividad = new Intent(this, MainActivity.class);
        startActivity(actividad);
        this.finish();
    }
}