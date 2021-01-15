package com.desapp.socialbox.models.pojos;

import java.util.ArrayList;

public class Usuario {
    int id;
    String nombre;
    String apellidos;
    String username;
    String profilePic;
    String status;
    ArrayList<Imagen> imagenes;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() { return profilePic; }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public ArrayList<Imagen> getImagenes() { return imagenes; }

    public void setImagenes(ArrayList<Imagen> imagenes) { this.imagenes = imagenes; }
}
