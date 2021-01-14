package com.desapp.socialbox.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.desapp.socialbox.EditInfoActivity;
import com.desapp.socialbox.MainActivity;
import com.desapp.socialbox.NavigationActivity;
import com.desapp.socialbox.R;
import com.desapp.socialbox.models.pojos.Usuario;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.JsonAdapter;
import com.desapp.socialbox.services.network.VolleyRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    String nombreUsuario;
    Usuario usuario;
    ImageView fotoPerfil;
    TextView nombre;
    TextView status;
    ImageButton editProfile;
    VolleyRequest volley;
    RequestQueue colaPeticiones;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editProfile = view.findViewById(R.id.editProfile);
        fotoPerfil = view.findViewById(R.id.profilePic);
        nombre = view.findViewById(R.id.NameView);
        status = view.findViewById(R.id.Status);
        Bundle bundle = getArguments();
        nombreUsuario = bundle.getString("user");
        volley = VolleyRequest.getInstance(getActivity());
        colaPeticiones = volley.getColaPeticiones();
        getAndSetProfileData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        return view;
    }

    private void getAndSetProfileData() {
        Map<String, String> params = new HashMap<>();
        params.put("username", nombreUsuario);

        JSONObject bodyRequest = new JSONObject(params);
        JsonObjectRequest getUserRequest = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.findAccountByUsername,
                bodyRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    usuario = JsonAdapter.UserAdapter(response);
                    setProfileData();
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Respuesta inesperada del servidor.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error al recuperar info\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        volley.agregarACola(getUserRequest);
    }

    private void setProfileData() {
        Picasso.get().load(usuario.getProfilePic()).into(fotoPerfil);
        nombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        status.setText(usuario.getStatus());
    }

    private  void showPopupMenu(View view) {
        PopupMenu menu = new PopupMenu(getContext(), view);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.menu_edit_profile);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.photo:
                modifyProfileData();
                return true;
            case R.id.info:
                Toast.makeText(getActivity(), "Presionnaste editar información", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void modifyProfileData() {
        Intent intent = new Intent(getContext(), EditInfoActivity.class);
        intent.putExtra("usuario", usuario.getUsername());
        intent.putExtra("nombre", usuario.getNombre());
        intent.putExtra("apellidos", usuario.getApellidos());
        intent.putExtra("estado", usuario.getStatus());
        getActivity().startActivity(intent);
    }
}