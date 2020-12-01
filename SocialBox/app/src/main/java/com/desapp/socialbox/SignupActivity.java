package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SignupActivity extends AppCompatActivity {
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        queue = Volley.newRequestQueue(this);
    }

    public void cancelarRegistro(View view){
        Intent actividad = new Intent(this, MainActivity.class);
        startActivity(actividad);
        this.finish();
    }
}