package com.desapp.socialbox.services.network;

import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.desapp.socialbox.models.pojos.Imagen;
import com.desapp.socialbox.models.pojos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonAdapter {

    public static Usuario signUpAdapter(JSONObject jsonObject) throws JSONException {
        Usuario response = new Usuario();

        response.setNombre(jsonObject.getString("nombre"));
        response.setApellidos(jsonObject.getString("apellidos"));
        response.setUsername(jsonObject.getString("username"));

        return response;
    }

    public static ArrayList<Usuario> SearchAdapter(JSONObject response) throws JSONException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        JSONArray usersData = response.getJSONArray("usuarios");

        for(int i = 0; i < usersData.length(); i++) {
            Usuario usuario = new Usuario();
            JSONObject jsonObject = usersData.getJSONObject(i);

            usuario.setId(jsonObject.getInt("id"));
            usuario.setNombre(jsonObject.getString("nombre"));
            usuario.setApellidos(jsonObject.getString("apellidos"));
            usuario.setUsername(jsonObject.getString("username"));
            usuario.setProfilePic(jsonObject.getString("profilePic"));
            usuario.setStatus(jsonObject.getString("status"));
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public static Usuario UserAdapter(JSONObject jsonObject) throws JSONException {
        Usuario usuario = new Usuario();
        JSONArray userImages = jsonObject.getJSONArray("pictures");
        ArrayList<Imagen> images = getImages(userImages);

        usuario.setId(jsonObject.getInt("id"));
        usuario.setNombre(jsonObject.getString("nombre"));
        usuario.setApellidos(jsonObject.getString("apellidos"));
        usuario.setUsername(jsonObject.getString("username"));
        usuario.setStatus(jsonObject.getString("status"));
        usuario.setProfilePic(jsonObject.getString("profilePic"));
        usuario.setImagenes(images);

        return usuario;
    }

    private static ArrayList<Imagen> getImages(JSONArray imagesData) throws JSONException {
        ArrayList<Imagen> imagenes = new ArrayList<>();
        String replaceSrc;

        for (int i = 0; i < imagesData.length(); i++) {
            Imagen imagen = new Imagen();
            JSONObject object = imagesData.getJSONObject(i);
            replaceSrc = object.getString("src").replace("./images/uploads/", "http://192.168.1.70:8080/api/users/uploads/");
            imagen.setUri(replaceSrc);
            imagen.setDescripcion(object.getString("descripcion"));
            imagenes.add(imagen);
        }

        return imagenes;
    }
}
