package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtherUserProfileActivity extends AppCompatActivity {
    String sender;
    private ImageView friendPicture;
    private TextView friedName;
    private TextView friendStatus;
    private Button addFriendBtn;
    private Usuario usuario;
    private VolleyRequest volley;
    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        friendPicture = findViewById(R.id.friendProfilePic);
        friedName = findViewById(R.id.friendName);
        friendStatus = findViewById(R.id.friendStatus);
        addFriendBtn = findViewById(R.id.addFriendAction);
        volley = VolleyRequest.getInstance(OtherUserProfileActivity.this);
        colaPeticiones = volley.getColaPeticiones();
        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        getAndSetData();
        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFriendRequest(v);
            }
        });
    }

    private void sendFriendRequest(final View view) {
        Map<String, String> params = new HashMap<>();
        params.put("sender", sender);
        params.put("receptorId", Integer.toString(usuario.getId()));

        JSONObject bodyRequest = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.addFriend,
                bodyRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addFriendBtn.setVisibility(View.GONE);
                Toast.makeText(OtherUserProfileActivity.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OtherUserProfileActivity.this, error.getClass().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        volley.agregarACola(request);
    }

    private void getAndSetData() {
        String username = getIntent().getStringExtra("username");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);

        JSONObject bodyRequest = new JSONObject(params);
        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.findAccountByUsername,
                bodyRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    usuario = JsonAdapter.UserAdapter(response);
                    setData();
                } catch (JSONException e) {
                    Toast.makeText(OtherUserProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OtherUserProfileActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        volley.agregarACola(searchRequest);
    }

    private void setData() {
        if (usuario != null) {
            Picasso.get().load(usuario.getProfilePic()).into(friendPicture);
            friedName.setText(usuario.getNombre() + " " + usuario.getApellidos());
            friendStatus.setText(usuario.getStatus());
        } else {
            Toast.makeText(OtherUserProfileActivity.this, "Error interno al cargar información del usuario",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}