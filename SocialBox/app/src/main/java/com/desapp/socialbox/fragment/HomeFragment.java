package com.desapp.socialbox.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.desapp.socialbox.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor usado en caso de que se desee pasar
     * paramétroa al crear
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // En caso de que se quiera dar funcionamiento
        // a un botón dentro de esta vista
        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        TextView etiquetaBienvenida = vista.findViewById(R.id.welcomeLbl);
        etiquetaBienvenida.setText("¡Hola, bienvenido!");
        return vista;
    }
}