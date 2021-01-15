package com.desapp.socialbox;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.desapp.socialbox.models.pojos.Usuario;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.JsonAdapter;
import com.desapp.socialbox.services.network.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFriendActivity extends Activity {
    List<Usuario> usuarios;
    String passedUsername;
    private EditText userName;
    private VolleyRequest volley;
    private RequestQueue colaPeticiones;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Intent intent = getIntent();
        passedUsername = intent.getStringExtra("user");
        userName = findViewById(R.id.userNameInput);
        volley = VolleyRequest.getInstance(AddFriendActivity.this);
        colaPeticiones = volley.getColaPeticiones();
        recyclerView = findViewById(R.id.userSearch);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userName.addTextChangedListener(searchAction);
    }

    private TextWatcher searchAction = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String input = userName.getText().toString().trim();
            if(input.length() > 0) {
                findUserAccount();
            } else {
                usuarios.clear();
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    private void findUserAccount() {
        Map<String, String> params = new HashMap<>();
        params.put("nombre", userName.getText().toString());

        JSONObject bodyRequest = new JSONObject(params);
        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.findAccount,
                bodyRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    usuarios = JsonAdapter.SearchAdapter(response);
                    MyRvAdapter adapter = new MyRvAdapter(AddFriendActivity.this, (ArrayList<Usuario>)usuarios, passedUsername);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AddFriendActivity.this));
                }catch (JSONException e) {
                    Toast.makeText(AddFriendActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddFriendActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        volley.agregarACola(searchRequest);
    }
}