package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button logInBtn;
    private EditText username;
    private EditText password;
    private VolleyRequest volley;
    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logInBtn = (Button) findViewById(R.id.logInBtn);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        volley = VolleyRequest.getInstance(MainActivity.this);
        colaPeticiones = volley.getColaPeticiones();
    }

    public void activarDesactivarBotonLogin(View vista) {
        if (camposLlenos()) {
            iniciarSesion();
        } else {
            Toast.makeText(MainActivity.this, "Se requiere usuario y contraseña",
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean camposLlenos() {
        if (username.getText().length() > 0 && password.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void iniciarSesion() {
        Map<String, String> params = new HashMap<>();
        params.put("username", username.getText().toString());
        params.put("contrasena", password.getText().toString());

        JSONObject bodyRequest = new JSONObject(params);
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.login,
                bodyRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean isAuthenticated = response.getBoolean("authenticated");
                    if (isAuthenticated) {
                        mostrarSesion();
                    } else {
                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta.",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Respuesta inesperada del servidor.",
                            Toast.LENGTH_LONG).show();
                    ;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        volley.agregarACola(loginRequest);
    }

    private void mostrarSesion() {
        Intent actividad = new Intent(this, NavigationActivity.class);
        String user = username.getText().toString();
        actividad.putExtra("user", user);
        startActivity(actividad);
        this.finish();
    }

    public void crearCuenta(View vista) {
        Intent actividad = new Intent(this, SignupActivity.class);
        startActivity(actividad);
        this.finish();
    }
}