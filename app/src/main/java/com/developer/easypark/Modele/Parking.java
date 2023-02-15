package com.developer.easypark.Modele;

public class Parking {

     public String nom;
     public int nbrPlace;
     public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Parking(String id , String nom, int nbrPlace) {
        this.id = id;
        this.nom = nom;
        this.nbrPlace = nbrPlace;
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
