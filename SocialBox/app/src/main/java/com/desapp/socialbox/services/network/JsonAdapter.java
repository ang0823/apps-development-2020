package com.desapp.socialbox.services.network;

import com.desapp.socialbox.models.pojos.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonAdapter {

    public static Usuario signUpAdapter(JSONObject jsonObject) throws JSONException {
        Usuario response = new Usuario();

        response.setNombre(jsonObject.getString("nombre"));
        response.setApellidos(jsonObject.getString("apellidos"));
        response.setEmail(jsonObject.getString("email"));

        return response;
    }
}
