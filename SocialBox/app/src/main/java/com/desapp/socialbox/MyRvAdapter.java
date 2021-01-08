package com.desapp.socialbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desapp.socialbox.models.pojos.Usuario;

import java.util.ArrayList;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewVolder> {
    ArrayList<Usuario> usuarios;
    Context context;

    public MyRvAdapter(Context context, ArrayList<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public MyViewVolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_user_result, parent, false);
        return new MyViewVolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewVolder holder, int position) {
        holder.userFullName.setText(usuarios.get(position).getNombre()
                + " " + usuarios.get(position).getApellidos());
        holder.userStatus.setText(usuarios.get(position).getStatus());
        holder.userUsername.setText(usuarios.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class MyViewVolder extends RecyclerView.ViewHolder {
        TextView userFullName;
        TextView userStatus;
        TextView userUsername;
        ImageView userProfilePicture;
        public MyViewVolder(@NonNull View itemView) {
            super(itemView);
            userFullName = itemView.findViewById(R.id.user_full_name);
            userStatus = itemView.findViewById(R.id.user_status);
            userUsername = itemView.findViewById(R.id.user_username);
            userProfilePicture = itemView.findViewById(R.id.user_profile_picture);
        }
    }
}
