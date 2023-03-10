package com.developer.easypark.Modele;

public class User {
    int id;
    String nom;
    String prenom;
    String mail;
    String password;
    int telephone;
    boolean is_admin;

    public User( String nom, String mail, String password) {

        this.nom = nom;
        this.mail = mail;
        this.password = password;
    }

    public User(String nom, String prenom, String mail, String password, int telephone, boolean is_admin) {

        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.telephone = telephone;
        this.is_admin = is_admin;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
}
