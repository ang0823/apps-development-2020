package com.desapp.socialbox.services.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequest {
    private static  VolleyRequest volleyR = null;
    private RequestQueue colaPeticiones;

    private VolleyRequest(){}

    private VolleyRequest(Context context){
        colaPeticiones = Volley.newRequestQueue(context);
    }

    public static VolleyRequest getInstance(Context context) {
        if (volleyR == null) {
            volleyR = new VolleyRequest(context);
        }

        return  volleyR;
    }

    public RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public void agregarACola(Request peticion) {
        if(peticion != null) {
            peticion.setTag(this);

            if (colaPeticiones == null) {
                colaPeticiones = getColaPeticiones();
            }

            peticion.setRetryPolicy(new DefaultRetryPolicy(1000, 3,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            colaPeticiones.add(peticion);
        }
    }
}
