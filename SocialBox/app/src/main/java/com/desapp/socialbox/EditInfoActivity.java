package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.VolleyRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditInfoActivity extends AppCompatActivity {
    TextView username;
    EditText nombre;
    EditText apellidos;
    EditText estado;
    Button save;
    VolleyRequest volley;
    RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        iniciarComponentes();
        setCurremtInfo();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateRequest(v);
            }
        });
    }

    private void iniciarComponentes() {
        username = findViewById(R.id.usernameView);
        nombre = findViewById(R.id.current_name);
        apellidos = findViewById(R.id.current_surname);
        estado = findViewById(R.id.current_status);
        save = findViewById(R.id.saveBtn);
        volley = VolleyRequest.getInstance(EditInfoActivity.this);
        colaPeticiones = volley.getColaPeticiones();
    }

    private void setCurremtInfo() {
        Intent intent = getIntent();
        username.setText(intent.getStringExtra("usuario"));
        nombre.setText(intent.getStringExtra("nombre"));
        apellidos.setText(intent.getStringExtra("apellidos"));
        estado.setText(intent.getStringExtra("estado"));
    }

    private void sendUpdateRequest(View view) {

        if (camposLlenos()) {
            Map params = new HashMap();
            params.put("username", username.getText());
            params.put("nombre", nombre.getText().toString());
            params.put("apellidos", apellidos.getText().toString());
            params.put("status", estado.getText().toString());


            JSONObject bodyRequest = new JSONObject(params);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ApiEndpoint.editProfile,
                    bodyRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(EditInfoActivity.this, "Información actualizada", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditInfoActivity.this,
                            "Ups! Tuvimos un error al actualizar tu información",
                            Toast.LENGTH_LONG).show();
                }
            });

            volley.agregarACola(request);
        } else {
            Toast.makeText(EditInfoActivity.this, "No se puede dejar campos vacíos", Toast.LENGTH_LONG).show();
        }

    }

    public boolean camposLlenos() {
        if (nombre.getText().length() > 0 && apellidos.getText().length() > 0 && estado.getText().length() > 0) {
            return true;
        }

        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}