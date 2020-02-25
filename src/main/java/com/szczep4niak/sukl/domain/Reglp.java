package com.szczep4niak.sukl.domain;

import java.util.UUID;

public class Reglp {
    private String kodSUKL;
    private String nazev;
    private String doplnek;
    private String sarze;
    private int mnozstvi;
    private double cena;
    private int typHlaseni;
    private int typPohybu;
    private int typOdberatele;
    private boolean primaDodavkaLP;
    private String polozkaID;

    public Reglp() {}

    public String getKodSUKL() {
        return kodSUKL;
    }

    public void setKodSUKL(String kodSUKL) {
        this.kodSUKL = kodSUKL;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getDoplnek() {
        return doplnek;
    }

    public void setDoplnek(String doplnek) {
        this.doplnek = doplnek;
    }

    public String getSarze() {
        return sarze;
    }

    public void setSarze(String sarze) {
        this.sarze = sarze;
    }

    public int getMnozstvi() {
        return mnozstvi;
    }

    public void setMnozstvi(int mnozstvi) {
        this.mnozstvi = mnozstvi;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getTypHlaseni() {
        return typHlaseni;
    }

    public void setTypHlaseni(int typHlaseni) {
        this.typHlaseni = typHlaseni;
    }

    public int getTypPohybu() {
        return typPohybu;
    }

    public void setTypPohybu(int typPohybu) {
        this.typPohybu = typPohybu;
    }

    public int getTypOdberatele() {
        return typOdberatele;
    }

    public void setTypOdberatele(int typOdberatele) {
        this.typOdberatele = typOdberatele;
    }

    public boolean isPrimaDodavkaLP() {
        return primaDodavkaLP;
    }

    public void setPrimaDodavkaLP(boolean primaDodavkaLP) {
        this.primaDodavkaLP = primaDodavkaLP;
    }

    public String getPolozkaID() {
        return polozkaID;
    }

    public void setPolozkaID(String polozkaID) {
        this.polozkaID = polozkaID;
    }

    @Override
    public String toString() {
        return "\nRegLp{" +
                "kodSUKL='" + kodSUKL + '\'' +
                ", nazev='" + nazev + '\'' +
                ", doplnek='" + doplnek + '\'' +
                ", sarze='" + sarze + '\'' +
                ", mnozstvi=" + mnozstvi +
                ", cena=" + cena +
                ", typHlaseni='" + typHlaseni + '\'' +
                ", typPohybu='" + typPohybu + '\'' +
                ", typOdberatele='" + typOdberatele + '\'' +
                ", primaDodavkaLP='" + primaDodavkaLP + '\'' +
                ", polozkaID='" + polozkaID + '\'' +
                '}';
    }
}
