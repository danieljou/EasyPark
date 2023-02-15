package com.developer.easypark.Modele;

public class Park {
    public String id;
    public boolean state;
    public String parkingID;

    public Park(String id, boolean state, String parkingID) {
        this.id = id;
        this.state = state;
        this.parkingID = parkingID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getParkingID() {
        return parkingID;
    }

    public void setParkingID(String parkingID) {
        this.parkingID = parkingID;
    }
}
