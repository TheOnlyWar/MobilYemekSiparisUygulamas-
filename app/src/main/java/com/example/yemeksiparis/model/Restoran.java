package com.example.yemeksiparis.model;

public class Restoran {
    private String restoranad;
    private String restoranadres;
    private String restoranmail;
    private String restorantel;

    public Restoran() {
    }

    public Restoran(String restoranad, String restoranadres, String restoranmail, String restorantel) {
        this.restoranad = restoranad;
        this.restoranadres = restoranadres;
        this.restoranmail = restoranmail;
        this.restorantel = restorantel;
    }

    public String getRestoranad() {
        return restoranad;
    }

    public void setRestoranad(String restoranad) {
        this.restoranad = restoranad;
    }

    public String getRestoranadres() {
        return restoranadres;
    }

    public void setRestoranadres(String restoranadres) {
        this.restoranadres = restoranadres;
    }

    public String getRestoranmail() {
        return restoranmail;
    }

    public void setRestoranmail(String restoranmail) {
        this.restoranmail = restoranmail;
    }

    public String getRestorantel() {
        return restorantel;
    }

    public void setRestorantel(String restorantel) {
        this.restorantel = restorantel;
    }
}

