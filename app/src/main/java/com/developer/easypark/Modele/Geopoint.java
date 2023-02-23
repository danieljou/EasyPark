package com.developer.easypark.Modele;

public class Geopoint {
    double lat;
    double log;

    public Geopoint() {
        this.log = 0;
        this.lat = 0;
    }

    public Geopoint(double lat, double log) {
        this.lat = lat;
        this.log = log;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
