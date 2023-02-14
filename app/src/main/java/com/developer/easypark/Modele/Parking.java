package com.developer.easypark.Modele;

public class Parking {

     public String nom;
     public int nbrPlace;


    public Parking( String nom, int nbrPlace) {

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
