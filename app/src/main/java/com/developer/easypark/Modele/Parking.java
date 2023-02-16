package com.developer.easypark.Modele;

import com.google.android.gms.maps.model.LatLng;

public class Parking {

     public String nom;
     public int nbrPlace;
     public String id;
     public LatLng latLng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Parking(String id, String nom, int nbrPlace, LatLng latLng) {
        this.nom = nom;
        this.nbrPlace = nbrPlace;
        this.id = id;
        this.latLng = latLng;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }
}
