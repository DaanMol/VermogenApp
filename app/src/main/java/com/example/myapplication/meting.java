package com.example.myapplication;

public class meting {
    private int id;
    private String naam;
    private String datum;
    private String oefening;
    private int gewicht;
    private String pointList;

    // constructor

    public meting(int id, String naam, String datum, String oefening, int gewicht, String pointList) {
        this.id = id;
        this.naam = naam;
        this.datum = datum;
        this.oefening = oefening;
        this.gewicht = gewicht;
        this.pointList = pointList;
    }

    public meting() {
    }

    // toString

    @Override
    public String toString() {
        return "meting{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", datum='" + datum + '\'' +
                ", oefening='" + oefening + '\'' +
                ", gewicht=" + gewicht + '\'' +
                ", pointList=" + pointList +
                '}';
    }


    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getOefening() {
        return oefening;
    }

    public void setOefening(String oefening) {
        this.oefening = oefening;
    }

    public int getGewicht() { return gewicht; }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public String getPointList() {
        return pointList;
    }

    public void setPointList(String naam) {
        this.pointList = pointList;
    }


}
