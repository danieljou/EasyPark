package com.developer.easypark.Modele;


import java.util.Date;

public class UsePark {
    public String id;
    public String parkID;
    public String userID;
    public Date dateDebut;
    public Date dateFin;

    public UsePark(String id, String parkID, String userID, Date dateDebut, Date dateFin) {
        this.id = id;
        this.parkID = parkID;
        this.userID = userID;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParkID() {
        return parkID;
    }

    public void setParkID(String parkID) {
        this.parkID = parkID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
