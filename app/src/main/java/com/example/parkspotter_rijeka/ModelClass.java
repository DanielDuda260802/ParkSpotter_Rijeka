package com.example.parkspotter_rijeka;

public class ModelClass {
    String parking_name;

    String status_sustava;
    String url;
    String kategorija;
    int kapacitet, slobodno;




    public ModelClass(String parking_name, String status_sustava, int kapacitet, int slobodno, String url, String kategorija)
    {
        this.parking_name = parking_name;
        this.status_sustava = status_sustava;
        this.kapacitet = kapacitet;
        this.slobodno = slobodno;
        this.url = url;
        this.kategorija = kategorija;
    }
    public String getParkingName() {
        return parking_name;
    }
    public void setStatus_sustava(String status_sustava) {
        this.status_sustava = status_sustava;
    }

    public String getStatus_sustava() {
        return status_sustava;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setSlobodno(int slobodno) {
        this.slobodno = slobodno;
    }

    public int getSlobodno() {
        return slobodno;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }


}
