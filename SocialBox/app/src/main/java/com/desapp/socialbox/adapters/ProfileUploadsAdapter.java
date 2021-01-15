package com.desapp.socialbox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desapp.socialbox.R;
import com.desapp.socialbox.models.pojos.Imagen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileUploadsAdapter extends RecyclerView.Adapter<ProfileUploadsAdapter.ProfileViewHolder> {
    ArrayList<Imagen> imagenes;
    Context context;

    public ProfileUploadsAdapter(Context contex, ArrayList<Imagen> imagenes) {
        this.context = contex;
        this.imagenes = imagenes;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_uploaded_picture, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.photoDescription.setText(imagenes.get(position).getDescripcion());
        Picasso.get().load(imagenes.get(position).getUri()).into(holder.image);
        holder.likesAmount.setText(imagenes.get(position).getCantidadLikes() + " Likes");
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView photoDescription;
        ImageView image;
        TextView likesAmount;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            photoDescription = itemView.findViewById(R.id.photo_desc);
            image = itemView.findViewById(R.id.image);
            likesAmount = itemView.findViewById(R.id.likes_amount);
        }
    }
}
