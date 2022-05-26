package com.example.yemeksiparis.model;

public class Kullanici {
    private String id;
    private String kullaniciad;
    private String kullanicisoyad;
    private String kullanicimail;
    private String kullanicisifre;
    private String kullaniciadres;
    private String kullanicitel;

    public Kullanici() {
    }

    public Kullanici(String id, String kullaniciad, String kullanicisoyad, String kullanicimail, String kullanicisifre, String kullaniciadres, String kullanicitel) {
        this.id = id;
        this.kullaniciad = kullaniciad;
        this.kullanicisoyad = kullanicisoyad;
        this.kullanicimail = kullanicimail;
        this.kullanicisifre = kullanicisifre;
        this.kullaniciadres = kullaniciadres;
        this.kullanicitel = kullanicitel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKullaniciad() {
        return kullaniciad;
    }

    public void setKullaniciad(String kullaniciad) {
        this.kullaniciad = kullaniciad;
    }

    public String getKullanicisoyad() {
        return kullanicisoyad;
    }

    public void setKullanicisoyad(String kullanicisoyad) {
        this.kullanicisoyad = kullanicisoyad;
    }

    public String getKullanicimail() {
        return kullanicimail;
    }

    public void setKullanicimail(String kullanicimail) {
        this.kullanicimail = kullanicimail;
    }

    public String getKullanicisifre() {
        return kullanicisifre;
    }

    public void setKullanicisifre(String kullanicisifre) {
        this.kullanicisifre = kullanicisifre;
    }

    public String getKullaniciadres() {
        return kullaniciadres;
    }

    public void setKullaniciadres(String kullaniciadres) {
        this.kullaniciadres = kullaniciadres;
    }

    public String getKullanicitel() {
        return kullanicitel;
    }

    public void setKullanicitel(String kullanicitel) {
        this.kullanicitel = kullanicitel;
    }
}
