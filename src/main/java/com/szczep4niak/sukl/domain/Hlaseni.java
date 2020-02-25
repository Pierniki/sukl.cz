package com.szczep4niak.sukl.domain;

import java.util.List;
import java.util.UUID;

public class Hlaseni {
    private String podaniID;
    private String kodPracoviste;
    private String obdobi;

    private Sw sw;

    private List<Reglp> reglp;
    private List<Nereglp> nereglp;

    public Hlaseni() {}

    public Hlaseni(String podaniID, String kodPracoviste, String obdobi) {
        this.podaniID = podaniID;
        this.kodPracoviste = kodPracoviste;
        this.obdobi = obdobi;
    }

    public String getPodaniID() {
        return podaniID;
    }

    public void setPodaniID(String podaniID) {
        this.podaniID = podaniID;
    }

    public String getKodPracoviste() {
        return kodPracoviste;
    }

    public void setKodPracoviste(String kodPracoviste) {
        this.kodPracoviste = kodPracoviste;
    }

    public String getObdobi() {
        return obdobi;
    }

    public void setObdobi(String obdobi) {
        this.obdobi = obdobi;
    }

    public Sw getSw() {
        return sw;
    }

    public void setSw(Sw sw) {
        this.sw = sw;
    }

    public List<Reglp> getReglp() {
        return reglp;
    }

    public void setReglp(List<Reglp> reglp) {
        this.reglp = reglp;
    }

    public List<Nereglp> getNereglp() {
        return nereglp;
    }

    public void setNereglp(List<Nereglp> nereglp) {
        this.nereglp = nereglp;
    }

    @Override
    public String toString() {
        return "Hlaseni{" +
                "podaniID='" + podaniID + '\'' +
                ", kodPracoviste='" + kodPracoviste + '\'' +
                ", obdobi='" + obdobi + '\'' +
                ", reglp=" + reglp +
                ", nereglp=" + nereglp +
                ", sw=" + sw +
                '}';
    }
}
