package com.desapp.socialbox.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.desapp.socialbox.R;
import com.desapp.socialbox.UploadPicActivity;
import com.desapp.socialbox.adapters.ProfileUploadsAdapter;
import com.desapp.socialbox.models.pojos.Usuario;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.JsonAdapter;
import com.desapp.socialbox.services.network.VolleyRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    final int PICK_IMAGE = 1;
    String nombreUsuario;
    Usuario usuario;
    ImageView fotoPerfil;
    TextView nombre;
    TextView status;
    ImageButton editProfile;
    FloatingActionButton btnAdd;
    VolleyRequest volley;
    RequestQueue colaPeticiones;
    Uri imageUri;
    Bitmap bitmap;
    RecyclerView photoAlbum;

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
        initializeCoponents(view);
        getAndSetProfileData();
        //ProfileUploadsAdapter adapter = new ProfileUploadsAdapter(getContext(), usuario.getImagenes());
        //photoAlbum.setAdapter(adapter);
        //photoAlbum.setLayoutManager(new LinearLayoutManager(getContext()));
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFile(v);
            }
        });

        return view;
    }

    private void initializeCoponents(View view) {
        Bundle bundle = getArguments();
        nombreUsuario = bundle.getString("user");
        editProfile = view.findViewById(R.id.editProfile);
        btnAdd = view.findViewById(R.id.btn_add);
        fotoPerfil = view.findViewById(R.id.profilePic);
        nombre = view.findViewById(R.id.NameView);
        status = view.findViewById(R.id.Status);
        photoAlbum = view.findViewById(R.id.photoAlbum);
        volley = VolleyRequest.getInstance(getActivity());
        colaPeticiones = volley.getColaPeticiones();
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
                modifyProfilePicture();
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

    private void modifyProfilePicture() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(gallery, "Seleccionar nueva foto"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                uploadImage();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error interno:\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage() {
        String image = bitmapToString();
        Map<String, String> params = new HashMap();
        params.put("username", nombreUsuario);
        params.put("image", image);

        JSONObject bodyRequest = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ApiEndpoint.modifyProfilePic,
                bodyRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    fotoPerfil.setImageBitmap(bitmap);
                    Toast.makeText(getContext(), response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                } catch(JSONException e) {
                    Toast.makeText(getContext(), "Foto actualizada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), ("Ups! No pudimos actualizar tu foto de perfil\n" + error.toString()), Toast.LENGTH_SHORT).show();
            }
        }) ;

        volley.agregarACola(request);
    }

    private String bitmapToString() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);
        byte[] imgBytes = byteArray.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


    private void uploadImageFile(View v) {
        Intent intent = new Intent(getContext(), UploadPicActivity.class);
        getActivity().startActivity(intent);
    }
}