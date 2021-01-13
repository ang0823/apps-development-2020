package com.desapp.socialbox.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.desapp.socialbox.AddFriendActivity;
import com.desapp.socialbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFragment extends Fragment {
    String usuario;

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        Button addFriend = view.findViewById(R.id.addFriendBtn);
        Bundle bundle = getArguments();
        usuario = bundle.getString("user");
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFriendActivity();
            }
        });

        return view;
    }

    public void openAddFriendActivity() {
        Intent intent = new Intent(getContext(), AddFriendActivity.class);
        getActivity().startActivity(intent);
    }
}