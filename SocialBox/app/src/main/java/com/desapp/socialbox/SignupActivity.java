package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.desapp.socialbox.models.pojos.Usuario;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.JsonAdapter;
import com.desapp.socialbox.services.network.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText nombre;
    private EditText apellidos;
    private EditText email;
    private EditText contrasena;
    private EditText confirmacionContrasna;

    private VolleyRequest volley;
    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nombre  = (EditText) findViewById(R.id.nameInput);
        apellidos = (EditText) findViewById(R.id.surnameInput);
        email = (EditText) findViewById(R.id.usernameInput);
        contrasena = (EditText) findViewById(R.id.passwordInput);
        confirmacionContrasna = (EditText) findViewById(R.id.repasswordInput);

        volley = VolleyRequest.getInstance(SignupActivity.this);
        colaPeticiones = volley.getColaPeticiones();
    }

    public void crearCuenta(View vista){
        Map<String, String> params = new HashMap<>();
        params.put("nombre", nombre.getText().toString());
        params.put("apellidos", apellidos.getText().toString());
        params.put("username", email.getText().toString());
        params.put("contrasena", contrasena.getText().toString());

        JSONObject jsonData = new JSONObject(params);

        JsonObjectRequest signUpRequest = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.signUp,
                jsonData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Usuario usuario = JsonAdapter.signUpAdapter(response);
                    mostrarSesion();
                } catch (JSONException e) {
                    Toast.makeText(SignupActivity.this, "Respuesta inesperada del servidor.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        volley.agregarACola(signUpRequest);
    }

    private void mostrarSesion() {
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void cancelarRegistro(View view){
        Intent actividad = new Intent(this, MainActivity.class);
        startActivity(actividad);
        this.finish();
    }
}