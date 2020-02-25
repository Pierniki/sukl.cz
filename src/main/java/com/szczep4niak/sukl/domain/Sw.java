package com.szczep4niak.sukl.domain;

public class Sw {
    private String nazev;
    private String verze;
    private String vyrobce;

    public Sw() { }

    public Sw(String nazev, String verze, String vyrobce) {
        this.nazev = nazev;
        this.verze = verze;
        this.vyrobce = vyrobce;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getVerze() {
        return verze;
    }

    public void setVerze(String verze) {
        this.verze = verze;
    }

    public String getVyrobce() {
        return vyrobce;
    }

    public void setVyrobce(String vyrobce) {
        this.vyrobce = vyrobce;
    }

    @Override
    public String toString() {
        return "Sw{" +
                "nazev='" + nazev + '\'' +
                ", varze='" + verze + '\'' +
                ", vyrobce='" + vyrobce + '\'' +
                '}';
    }
}
